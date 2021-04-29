package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Sql(value = {"/data_init.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DogServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogService dogService;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    private Dog dog;


    @BeforeEach
    void setUp() throws IOException {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        LocalDate birthday = LocalDate.of(2019, 10, 4);
        byte [] fileContent = this.getClass().getClassLoader().getResourceAsStream("test_img.png").readAllBytes();
        dog = Dog.builder()
                .name("Belka")
                .breed("Laika")
                .dateOfBirth(birthday)
                .gender(Gender.FEMALE)
                .profilePicture(fileContent)
                .owner(userRepository.getOne(1L)).build();
        dogRepository.saveAndFlush(dog);
    }

    @Test
    public void testAddDog() {
        Dog persistedDog = dogService.addDog(dog);

        assertEquals(dog.getName(), persistedDog.getName());
        assertEquals(dog.getBreed(), persistedDog.getBreed());
        assertEquals(dog.getGender().toString(), persistedDog.getGender().toString());
        assertEquals(dog.getDateOfBirth(), persistedDog.getDateOfBirth());
        assertEquals(dog.getOwner().getId(), persistedDog.getOwner().getId());
        assertEquals(dog.getProfilePicture(), persistedDog.getProfilePicture());
    }

    @Test
    public void testAddDogUnauthorizedUser() {
        dog.setOwner(userRepository.getOne(2L));

        assertThrows(ResponseStatusException.class, () -> dogService.addDog(dog));
    }

    @Test
    public void testDeleteDogSuccess() {
        dogService.deleteDog(dog.getOwner().getId(), dog.getId());
        Optional<Dog> notExistingDog = dogRepository.findById(dog.getId());
        assertTrue(notExistingDog.isEmpty());
    }

    @Test
    public void testDeleteNotExistingDog() {
        assertThrows(ResponseStatusException.class, () -> dogService.deleteDog(1L, 3L));
    }

    @Test
    public void testDeleteDogUnauthorizedUser() {
        assertThrows(ResponseStatusException.class, () -> dogService.deleteDog(3L , dog.getId()));
    }
}
