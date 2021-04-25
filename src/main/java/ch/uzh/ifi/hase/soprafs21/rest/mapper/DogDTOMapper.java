package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Mapper
public interface DogDTOMapper {
    DogDTOMapper INSTANCE = Mappers.getMapper(DogDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "breed", target = "breed")
    @Mapping(source = "gender", target = "sex")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "profilePicture", target = "profilePicture")
    DogDto toDogDTO(Dog entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "breed", target = "breed")
    @Mapping(source = "sex", target = "gender")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "profilePicture", target = "profilePicture")
    Dog toDogEntity(DogDto dogDto) throws IOException;

    default Resource map(byte[] value) {
        if(value == null) {
            return null;
        }
        return new ByteArrayResource(value);
    }

    default byte[] map(Resource value) throws IOException {
        if(value == null) {
            return null;
        }
        return value.getInputStream().readAllBytes();
    }
}
