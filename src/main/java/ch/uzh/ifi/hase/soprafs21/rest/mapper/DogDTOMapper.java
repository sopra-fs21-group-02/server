package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DogDTOMapper {
    DogDTOMapper INSTANCE = Mappers.getMapper(DogDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "breed", target = "breed")
    @Mapping(source = "gender", target = "sex")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "bio", target = "description")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "profilePicture", target = "profilePicture")
    DogDto toDogDTO(Dog entity);
}
