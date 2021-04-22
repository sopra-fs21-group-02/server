package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Named;

@Named("latestLocation")
public class LocationMapper {

    @Named("PointToCoordinate")
    public CoordinateDto map(Point latestLocation) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(latestLocation.getX());
        coordinateDto.setLongitude(latestLocation.getY());
        return coordinateDto;
    }
}
