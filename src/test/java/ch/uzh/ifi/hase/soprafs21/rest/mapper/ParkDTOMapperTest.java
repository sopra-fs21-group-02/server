package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ParkDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ParkDTOMapperTest {

    @Mock
    private GeometryFactory geometryFactoryMock;

    private User user;

    @BeforeEach
    void initialization() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).build();
    }

    @Test
    void toParkDTO() {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(8.461859195948641, 47.35997146785179));
        Park park = Park.builder().id(1L).creator(user).coordinate(newLocation).build();

        ParkDto parkDto = ParkDTOMapper.INSTANCE.toParkDTO(park);

        assertEquals(1L, parkDto.getId());
        assertEquals(user.getId(), parkDto.getCreatorId());
        assertEquals(newLocation.getX(), parkDto.getCoordinate().getLongitude());
        assertEquals(newLocation.getY(), parkDto.getCoordinate().getLatitude());
    }

    @Test
    void toParkEntity() {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(8.461859195948641, 47.35997146785179));

        ParkDto parkDto = new ParkDto();
        parkDto.setCreatorId(1L);
        parkDto.setCoordinate(SpatialDTOMapper.INSTANCE.getCoordinateDTO(newLocation));

        when(geometryFactoryMock.createPoint(any(Coordinate.class))).thenReturn(newLocation);
        Park park = ParkDTOMapper.INSTANCE.toParkEntity(parkDto, user, geometryFactoryMock);

        assertEquals(user.getId(), park.getCreator().getId());
        assertEquals(newLocation.getX(), park.getCoordinate().getX());
        assertEquals(newLocation.getY(), park.getCoordinate().getY());
    }
}