package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ParkService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParksApiControllerTest {

    @Mock
    private ParkService parkServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private GeometryFactory geometryFactoryMock;

    private User creator;

    private ParkDto parkDto;

    @Mock
    private Point location;

    @InjectMocks
    private ParksApiController parksApiController;

    @Mock
    private Polygon polygon;

    @Mock
    private Park park1Mock;

    @Mock
    private Park park2Mock;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        creator = User.builder().id(1L).name("Piter").email("mark@twen.de").build();
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLongitude(25.5545);
        coordinateDto.setLatitude(8.6743);

        parkDto = new ParkDto();
        parkDto.setCreatorId(creator.getId());
        parkDto.setCoordinate(coordinateDto);
    }

    @Test
    void testAddParkSuccess() throws Exception {
        when(userServiceMock.getUserById(eq(1L))).thenReturn(creator);
        when(geometryFactoryMock.createPoint(Mockito.any(Coordinate.class))).thenReturn(location);
        ResponseEntity<Void> responseEntity = parksApiController.addPark(parkDto);
        verify(parkServiceMock).addPark(Mockito.any(Park.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testAddParkWithId() {
        parkDto.setId(1L);

        Exception exception = assertThrows(ResponseStatusException.class, () -> parksApiController.addPark(parkDto));

        String expectedMessage = "Id is not allowed in POST";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getParksByArea() throws Exception {
        CoordinateDto coordinateDto1 = new CoordinateDto();
        coordinateDto1.setLongitude(4.5555);
        coordinateDto1.setLatitude(47.5555);
        CoordinateDto coordinateDto2 = new CoordinateDto();
        coordinateDto2.setLongitude(5.5585);
        coordinateDto2.setLatitude(25.5545);
        CoordinateDto coordinateDto3 = new CoordinateDto();
        coordinateDto3.setLongitude(8.5585);
        coordinateDto3.setLatitude(22.5545);
        AreaFilterDto areaFilter = new AreaFilterDto();
        areaFilter.addVisibleAreaItem(coordinateDto1).addVisibleAreaItem(coordinateDto2).addVisibleAreaItem(coordinateDto3);
        Coordinate [] coordinates = SpatialDTOMapper.INSTANCE.getCoordinates(areaFilter.getVisibleArea());
        when(geometryFactoryMock.createPolygon(eq (coordinates))).thenReturn(polygon);

        when(park1Mock.getCreator()).thenReturn(creator);
        when(park2Mock.getCreator()).thenReturn(creator);
        List<Park> listParks = Arrays.asList(park1Mock, park2Mock);
        when(parkServiceMock.getAllParksInArea(any())).thenReturn(listParks);

        ResponseEntity<List<ParkDto>> entity = parksApiController.getParks(areaFilter);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(2, entity.getBody().size());
    }

    @Test
    void testDeletePark() throws Exception {
        assertEquals(HttpStatus.NO_CONTENT, parksApiController.deletePark(1L).getStatusCode());
        verify(parkServiceMock).deletePark(eq(1L));
    }
}