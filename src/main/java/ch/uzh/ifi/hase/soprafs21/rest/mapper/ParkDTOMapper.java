package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ParkDto;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserDTOMapper.class, SpatialDTOMapper.class})
public interface ParkDTOMapper {
    ParkDTOMapper INSTANCE = Mappers.getMapper(ParkDTOMapper.class);

    SpatialDTOMapper SPATIAL_MAPPER = Mappers.getMapper(SpatialDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(expression = "java(entity.getCreator() != null? entity.getCreator().getId(): null)", target = "creatorId")
    @Mapping(source = "coordinate", target = "coordinate")
    @Mapping(source = "description", target = "description")
    ParkDto toParkDTO(Park entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(creator)", target = "creator")
    @Mapping(expression = "java(parkDto.getDescription())", target = "description")
    @Mapping(expression = "java(SPATIAL_MAPPER.getPoint(parkDto.getCoordinate(), geometryFactory))", target = "coordinate")
    Park toParkEntity(ParkDto parkDto, User creator, GeometryFactory geometryFactory);

    List<ParkDto> toParkDTOList(List<Park> entities);
}
