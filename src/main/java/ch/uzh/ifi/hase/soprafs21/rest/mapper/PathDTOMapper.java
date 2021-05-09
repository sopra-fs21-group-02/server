package ch.uzh.ifi.hase.soprafs21.rest.mapper;


import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PathDTOMapper {
    PathDTOMapper INSTANCE = Mappers.getMapper(PathDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "distance", target = "distance")
    @Mapping(expression = "java((new org.locationtech.jts.geom.GeometryFactory()).createLineString(SpatialDTOMapper.INSTANCE.getCoordinates(routeDto.getListOfCoordinates())))", target = "route")
    Path toPathEntity(WalkingRouteDto routeDto);
}
