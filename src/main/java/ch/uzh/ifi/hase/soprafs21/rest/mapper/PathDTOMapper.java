package ch.uzh.ifi.hase.soprafs21.rest.mapper;


import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SpatialDTOMapper.class})
public interface PathDTOMapper {
    PathDTOMapper INSTANCE = Mappers.getMapper(PathDTOMapper.class);
    SpatialDTOMapper SPATIAL_MAPPER = Mappers.getMapper(SpatialDTOMapper.class);

    @Mapping(expression = "java(routeDto.getId())", target = "id")
    @Mapping(expression = "java(routeDto.getDistance())", target = "distance")
    @Mapping(expression = "java(creator)", target = "creator")
    @Mapping(expression = "java(SPATIAL_MAPPER.getLineString(routeDto.getListOfCoordinates(), geometryFactory))", target = "route")
    Path toPathEntity(WalkingRouteDto routeDto, GeometryFactory geometryFactory, User creator);
}
