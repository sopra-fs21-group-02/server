package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SpatialDTOMapper {

    SpatialDTOMapper INSTANCE = Mappers.getMapper(SpatialDTOMapper.class);


    default Coordinate getCoordinate(CoordinateDto coordinateDto){
        return new Coordinate(coordinateDto.getLongitude(), coordinateDto.getLatitude());
    }

    default CoordinateDto getCoordinateDTO(Point lastUserLocation) {
        if(lastUserLocation == null){
            return null;
        }
        return new CoordinateDto().longitude(lastUserLocation.getX()).latitude(lastUserLocation.getY());
    }

    Coordinate[] getCoordinates(List<CoordinateDto> coordinateDtoList);
}
