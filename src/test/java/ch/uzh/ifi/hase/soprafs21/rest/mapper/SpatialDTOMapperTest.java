package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import static org.junit.jupiter.api.Assertions.*;

class SpatialDTOMapperTest {

    @Test
    void getCoordinate() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLongitude(8.461859195948641);
        coordinateDto.setLatitude(47.35997146785179);

        Coordinate coordinate = SpatialDTOMapper.INSTANCE.getCoordinate(coordinateDto);

        assertEquals(coordinateDto.getLongitude(), coordinate.getX());
        assertEquals(coordinateDto.getLatitude(), coordinate.getY());
    }

    @Test
    void getCoordinateDTOLocationIsNull() {
        CoordinateDto coordinateDto = SpatialDTOMapper.INSTANCE.getCoordinateDTO(null);
        assertNull(coordinateDto);
    }

    @Test
    void getCoordinateDTO() {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(8.461859195948641, 47.35997146785179));
        CoordinateDto coordinateDto = SpatialDTOMapper.INSTANCE.getCoordinateDTO(newLocation);

        assertEquals(newLocation.getX(), coordinateDto.getLongitude());
        assertEquals(newLocation.getY(), coordinateDto.getLatitude());
    }

}