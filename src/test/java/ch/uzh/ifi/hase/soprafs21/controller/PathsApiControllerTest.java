package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import ch.uzh.ifi.hase.soprafs21.service.PathService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class PathsApiControllerTest {

    @InjectMocks
    private PathsApiController pathsApiController;

    @Mock
    private GeometryFactory geometryFactoryMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private PathService pathServiceMock;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPathWithId() {
        WalkingRouteDto routeDto =new WalkingRouteDto();
        routeDto.setId(1L);
        assertThrows(ResponseStatusException.class, () -> pathsApiController.addPath(routeDto));
    }

    @Test
    void testAddPathInvalidUser() {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        userOverviewDto.setStatus(OnlineStatusDto.ONLINE);

        WalkingRouteDto routeDto =new WalkingRouteDto();
        routeDto.setCreator(userOverviewDto);
        routeDto.setDistance(5.5d);

        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(userOverviewDto.getId())).willReturn(Boolean.FALSE);
        assertThrows(ResponseStatusException.class, () -> pathsApiController.addPath(routeDto));
    }

    @Test
    void testAddPathSuccess() throws Exception {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        userOverviewDto.setStatus(OnlineStatusDto.ONLINE);

        User mockedUser = User.builder().id(1L).email("mark@twen.de").name("mark").profilePictureURL("SomeURL").
                provider("SomeProvider").providerUid("SomeGoogleId").status(OnlineStatus.ONLINE).build();


        List<CoordinateDto> coordinateDtoList = new ArrayList<>();
        CoordinateDto coordinateDto1 = new CoordinateDto();
        coordinateDto1.setLongitude(4.5555);
        coordinateDto1.setLatitude(47.5555);
        CoordinateDto coordinateDto2 = new CoordinateDto();
        coordinateDto2.setLongitude(5.5585);
        coordinateDto2.setLatitude(25.5545);
        CoordinateDto coordinateDto3 = new CoordinateDto();
        coordinateDto3.setLongitude(8.5585);
        coordinateDto3.setLatitude(22.5545);
        coordinateDtoList.add(coordinateDto1);
        coordinateDtoList.add(coordinateDto2);
        coordinateDtoList.add(coordinateDto3);

        WalkingRouteDto routeDto =new WalkingRouteDto();
        routeDto.setCreator(userOverviewDto);
        routeDto.setDistance(5.5d);
        routeDto.setListOfCoordinates(coordinateDtoList);
        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(userOverviewDto.getId())).willReturn(Boolean.TRUE);
        given(userServiceMock.getUserById(userOverviewDto.getId())).willReturn(mockedUser);


        ResponseEntity<Void> responseEntity = pathsApiController.addPath(routeDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
