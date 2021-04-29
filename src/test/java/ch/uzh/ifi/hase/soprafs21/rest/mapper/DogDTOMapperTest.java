package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.GenderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DogDTOMapperTest {
    private Dog dog;
    private DogDto dogDto;
    private MultipartFile resource;

    @BeforeEach
    void initialization() throws IOException {
        User owner = User.builder().id(1L).email("henry@dogs.de").status(OnlineStatus.ONLINE).name("Henry").profilePictureURL("Some picture").build();
        LocalDate birthday = LocalDate.of(2019, 10, 4);
        byte [] fileContent = this.getClass().getClassLoader().getResourceAsStream("test_img.png").readAllBytes();
        dog = Dog.builder().id(1L).name("Belka").breed("Laika").dateOfBirth(birthday).gender(Gender.FEMALE).profilePicture(fileContent).owner(owner).build();

        resource = new MockMultipartFile("test.png", fileContent);
        dogDto = new DogDto();
        dogDto.setId(2L);
        dogDto.setName("Strelka");
        dogDto.breed("Laika");
        dogDto.setSex(GenderDto.FEMALE);
        dogDto.setDateOfBirth(birthday);
    }

    @Test
    void toDogDTO() {
        DogDto dogDto = DogDTOMapper.INSTANCE.toDogDTO(dog);

        assertEquals(dog.getId(), dogDto.getId());
        assertEquals(dog.getName(), dogDto.getName());
        assertEquals(dog.getBreed(), dogDto.getBreed());
        assertEquals(dog.getDateOfBirth(), dogDto.getDateOfBirth());
        assertEquals(dog.getGender().toString(), dogDto.getSex().toString());
    }


    @Test
    void toDogEntity() throws IOException {
        Dog newDog = DogDTOMapper.INSTANCE.toDogEntity(dogDto, resource);

        assertEquals(dogDto.getId(), newDog.getId());
        assertEquals(dogDto.getName(), newDog.getName());
        assertEquals(dogDto.getBreed(), newDog.getBreed());
        assertEquals(dogDto.getDateOfBirth(), newDog.getDateOfBirth());
        assertEquals(dogDto.getSex().toString(), newDog.getGender().toString());
        assertArrayEquals(resource.getBytes(), newDog.getProfilePicture());
    }

    @Test
    void toDogEntityNoPicture() throws IOException {
        Dog dog = DogDTOMapper.INSTANCE.toDogEntity(dogDto, null);
        assertNull(dog.getProfilePicture());
    }
}