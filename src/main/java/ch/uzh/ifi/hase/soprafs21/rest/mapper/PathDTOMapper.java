package ch.uzh.ifi.hase.soprafs21.rest.mapper;


import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SpatialDTOMapper.class,UserDTOMapper.class})
public interface PathDTOMapper {
    PathDTOMapper INSTANCE = Mappers.getMapper(PathDTOMapper.class);
    SpatialDTOMapper SPATIAL_MAPPER = Mappers.getMapper(SpatialDTOMapper.class);
    UserDTOMapper USER_DTO_MAPPER =Mappers.getMapper(UserDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(routeDto.getDistance())", target = "distance")
    @Mapping(expression = "java(creator)", target = "creator")
    @Mapping(expression = "java(SPATIAL_MAPPER.getLineString(routeDto.getListOfCoordinates(), geometryFactory))", target = "route")
    Path toPathEntity(WalkingRouteDto routeDto, GeometryFactory geometryFactory, User creator);

    @Mapping(expression = "java(pathEntity.getId())", target = "id")
    @Mapping(expression = "java(pathEntity.getDistance())", target = "distance")
    @Mapping(expression = "java(USER_DTO_MAPPER.toOverviewDTO(pathEntity.getCreator()))",target = "creator")
    @Mapping(expression = "java(SPATIAL_MAPPER.getCoordinateDTOList(pathEntity.getRoute()))", target="listOfCoordinates")
    WalkingRouteDto toRouteDto(Path pathEntity);

    List<WalkingRouteDto> toWalkingRouteDtoList(List<Path> pathInPolygon);
}
