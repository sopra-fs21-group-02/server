package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessagePostDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ParkDto;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.ParkService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
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
}