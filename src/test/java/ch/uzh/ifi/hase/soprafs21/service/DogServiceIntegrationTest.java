package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    void testAddDog() {
        Dog persistedDog = dogService.addDog(dog);

        assertEquals(dog.getName(), persistedDog.getName());
        assertEquals(dog.getBreed(), persistedDog.getBreed());
        assertEquals(dog.getGender().toString(), persistedDog.getGender().toString());
        assertEquals(dog.getDateOfBirth(), persistedDog.getDateOfBirth());
        assertEquals(dog.getOwner().getId(), persistedDog.getOwner().getId());
        assertEquals(dog.getProfilePicture(), persistedDog.getProfilePicture());
    }

    @Test
    void testAddDogUnauthorizedUser() {
        dog.setOwner(userRepository.getOne(2L));

        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.addDog(dog));

        String expectedMessage = "User is not permitted to manipulate this dog";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteDogSuccess() {
        dogService.deleteDog(dog.getOwner().getId(), dog.getId());
        Optional<Dog> notExistingDog = dogRepository.findById(dog.getId());
        assertTrue(notExistingDog.isEmpty());
    }

    @Test
    void testDeleteNotExistingDog() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.deleteDog(1L, 3L));

        String expectedMessage = "No Dog with provided id exists";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteDogUnauthorizedUser() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.deleteDog(3L , dog.getId()));

        String expectedMessage = "User is not permitted to delete another user dogs";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEditDogSuccess() {
        Dog dogWithUpdatedFields = Dog.builder()
                .id(dog.getId())
                .name("Strelka")
                .gender(dog.getGender())
                .breed(dog.getBreed())
                .dateOfBirth(LocalDate.of(2020, 10, 4))
                .profilePicture(null)
                .owner(dog.getOwner())
                .build();
        Dog updatedDog = dogService.editDog(dogWithUpdatedFields);

        assertEquals(dogWithUpdatedFields.getName(), updatedDog.getName());
        assertEquals(dogWithUpdatedFields.getBreed(), updatedDog.getBreed());
        assertEquals(dogWithUpdatedFields.getGender().toString(), updatedDog.getGender().toString());
        assertEquals(dogWithUpdatedFields.getDateOfBirth(), updatedDog.getDateOfBirth());
        assertEquals(dogWithUpdatedFields.getOwner().getId(), updatedDog.getOwner().getId());
        assertArrayEquals(dog.getProfilePicture(), updatedDog.getProfilePicture());
    }
    
    @Test
    void testEditNotExistingDog() {
        dog.setId(77L);
        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.editDog(dog));

        String expectedMessage = "No Dog with provided id exists";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEditDogUnauthorizedUser() {
        dog.setOwner(userRepository.getOne(2L));
        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.editDog(dog));

        String expectedMessage = "User is not permitted to edit another user dogs";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEditDogNotExistingUser() {
        User newUser = User.builder().id(66L).build();
        dog.setOwner(newUser);
        Exception ex = assertThrows(ResponseStatusException.class, () -> dogService.editDog(dog));

        String expectedMessage = "No User with provided id exists";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}
