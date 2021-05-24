package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.PathService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class PathsApiControllerTest {

    @InjectMocks
    private PathsApiController pathsApiController;

    @Mock
    private GeometryFactory geometryFactoryMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private PathService pathServiceMock;

    @Mock
    private Polygon polygon;

    @Mock
    private Path path1Mock;

    @Mock
    private Path path2Mock;

    private User creator;
    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;


    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
        creator = User.builder().id(1L).name("Piter").email("mark@twen.de").build();
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
        routeDto.setDescription("Path1");

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
        routeDto.setDescription("Path2");
        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(userOverviewDto.getId())).willReturn(Boolean.TRUE);
        given(userServiceMock.getUserById(userOverviewDto.getId())).willReturn(mockedUser);


        ResponseEntity<Void> responseEntity = pathsApiController.addPath(routeDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void deletePathWithId_Success() throws Exception {
        long pathId =1L;
        ResponseEntity<Void> responseEntity = pathsApiController.deletePath(pathId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void getPathsByArea() throws Exception {
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
        AreaFilterDto areaFilter = new AreaFilterDto();
        areaFilter.addVisibleAreaItem(coordinateDto1).addVisibleAreaItem(coordinateDto2).addVisibleAreaItem(coordinateDto3);
        Coordinate[] coordinates = SpatialDTOMapper.INSTANCE.getCoordinates(areaFilter.getVisibleArea());
        when(geometryFactoryMock.createPolygon(coordinates)).thenReturn(polygon);

        when(path1Mock.getCreator()).thenReturn(creator);
        when(path2Mock.getCreator()).thenReturn(creator);
        List<Path> listPaths = Arrays.asList(path1Mock, path2Mock);
        when(pathServiceMock.getAllPathsInArea(any())).thenReturn(listPaths);

        ResponseEntity<List<WalkingRouteDto>> entity = pathsApiController.getPaths(areaFilter);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(2, entity.getBody().size());
    }
}
