package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper
public interface DogDTOMapper {
    DogDTOMapper INSTANCE = Mappers.getMapper(DogDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "breed", target = "breed")
    @Mapping(source = "gender", target = "sex")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    DogDto toDogDTO(Dog entity);

    @Mapping(expression = "java(dogDto.getId())", target = "id")
    @Mapping(expression = "java(dogDto.getName())", target = "name")
    @Mapping(expression = "java(dogDto.getBreed())", target = "breed")
    @Mapping(expression = "java(Gender.fromValue(dogDto.getSex().toString()))", target = "gender")
    @Mapping(expression = "java(dogDto.getDateOfBirth())", target = "dateOfBirth")
    @Mapping(expression = "java(map(profilePicture))", target = "profilePicture")
    @Mapping(expression = "java(owner)", target = "owner")
    @Mapping(expression = "java(profilePicture!=null?profilePicture.getContentType():null)", target = "profilePictureContentType")
    Dog toDogEntity(DogDto dogDto, MultipartFile profilePicture, User owner) throws IOException;

    default Resource map(byte[] value) {
        if(value == null) {
            return null;
        }
        return new ByteArrayResource(value);
    }

    default byte[] map(MultipartFile value) throws IOException {
        if(value == null) {
            return null;
        }
        return value.getResource().getInputStream().readAllBytes();
    }
}
