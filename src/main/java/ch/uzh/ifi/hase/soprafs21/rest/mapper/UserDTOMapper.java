package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g., UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for creating information (POST).
 */
@Mapper(uses = LocationMapper.class)
public interface UserDTOMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "profilePicture", target = "profilePictureURL")
    @Mapping(source = "tags", target = "tags")
    @Mapping(source = "dogs", target = "listOfDogs")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "bio", target = "bio")
    User convertUserPostDTOtoEntity(UserDto userDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "profilePictureURL", target = "profilePicture")
    @Mapping(source = "tags", target = "tags")
    @Mapping(source = "listOfDogs", target = "dogs")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "lastUserLocation", target = "latestLocation", qualifiedByName = { "latestLocation", "PointToCoordinate" })
    UserDto convertEntityToUserDTO(User user);

   /* @Named("latestLocation")
    default CoordinateDto map(Point latestLocation) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(latestLocation.getX());
        coordinateDto.setLongitude(latestLocation.getY());
        return coordinateDto;
    }*/

    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "profilePictureURL", target = "profilePicture")
    @Mapping(source = "status", target = "status")
    UserOverviewDto toOverviewDTO(User entity);
}
