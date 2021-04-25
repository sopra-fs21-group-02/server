package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.GenderDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DogDTOMapperTest {
    private Dog dog;
    private DogDto dogDto;

    @BeforeEach
    void initialization() throws IOException {
        User owner = User.builder().id(1L).email("henry@dogs.de").status(OnlineStatus.ONLINE).name("Henry").profilePictureURL("Some picture").build();
        LocalDate birthday = LocalDate.of(2019, 10, 4);
        byte [] fileContent = this.getClass().getClassLoader().getResourceAsStream("test_img.png").readAllBytes();
        dog = Dog.builder().id(1L).name("Belka").breed("Laika").dateOfBirth(birthday).gender(Gender.FEMALE).profilePicture(fileContent).owner(owner).build();

        ByteArrayResource resource = new ByteArrayResource(fileContent);
        dogDto = new DogDto();
        dogDto.setId(2L);
        dogDto.setName("Strelka");
        dogDto.breed("Laika");
        dogDto.setSex(GenderDto.FEMALE);
        dogDto.setDateOfBirth(birthday);
        dogDto.setProfilePicture(resource);
    }

    @Test
    void toDogDTO() throws IOException {
        DogDto dogDto = DogDTOMapper.INSTANCE.toDogDTO(dog);

        assertEquals(dog.getId(), dogDto.getId());
        assertEquals(dog.getName(), dogDto.getName());
        assertEquals(dog.getBreed(), dogDto.getBreed());
        assertEquals(dog.getDateOfBirth(), dogDto.getDateOfBirth());
        assertEquals(dog.getGender().toString(), dogDto.getSex().toString());
        assertArrayEquals(dog.getProfilePicture(), dogDto.getProfilePicture().getInputStream().readAllBytes());
    }

    @Test
    void toDTONoPicture() {
        dog.setProfilePicture(null);

        DogDto dogDto = DogDTOMapper.INSTANCE.toDogDTO(dog);
        assertNull(dogDto.getProfilePicture());
    }

    @Test
    void toDogEntity() throws IOException {
        Dog newDog = DogDTOMapper.INSTANCE.toDogEntity(dogDto);

        assertEquals(dogDto.getId(), newDog.getId());
        assertEquals(dogDto.getName(), newDog.getName());
        assertEquals(dogDto.getBreed(), newDog.getBreed());
        assertEquals(dogDto.getDateOfBirth(), newDog.getDateOfBirth());
        assertEquals(dogDto.getSex().toString(), newDog.getGender().toString());
        assertArrayEquals(dogDto.getProfilePicture().getInputStream().readAllBytes(), newDog.getProfilePicture());
    }

    @Test
    void toDogEntityNoPicture() throws IOException {
        dogDto.setProfilePicture(null);

        Dog dog = DogDTOMapper.INSTANCE.toDogEntity(dogDto);
        assertNull(dog.getProfilePicture());
    }
}