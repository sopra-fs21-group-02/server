package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.repository.PathRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(value = {"/data_init.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PathServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Mock
    private PathRepository pathRepository;

    @Autowired
    private GeometryFactory geometryFactory;

    @Autowired
    private PathService pathService;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    private Path path;

    private Path path_1;

    @BeforeEach
    void setUp() {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        Coordinate [] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(8.432962, 47.378622);
        coordinates[1] = new Coordinate(8.474358, 47.373311);
        coordinates[2] = new Coordinate( 8.470429, 47.357797);
        coordinates[3] = new Coordinate(8.437273, 47.362384);
        coordinates[4] = new Coordinate(8.432962, 47.378622);
        LineString routeCoordinates = geometryFactory.createLineString(coordinates);
        path = Path.builder().route(routeCoordinates).creator(userRepository.getOne(1L)).distance(2.0).build();
        path_1 = Path.builder().route(routeCoordinates).creator(userRepository.getOne(2L)).distance(2.0).build();
    }

    @Test
    void testAddPath() {
        Path persistedPath = pathService.savePath(path);
        assertEquals(path.getCreator().getId(), persistedPath.getCreator().getId());
        assertEquals(path.getDistance(), persistedPath.getDistance());
    }

    @Test
    void testDeletePath(){
        Path persistedPath = pathService.savePath(path);
        this.pathService.deletePath(persistedPath.getId());
        assertEquals(Optional.empty(), pathRepository.findById(persistedPath.getId()));
    }

    @Test
    void testDeletePath_withUnauthenticateUser(){

        Path persistedPath = pathService.savePath(path_1);
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            pathService.deletePath(persistedPath.getId());
        });

        String expectedMessage = "User is not permitted to delete another user paths";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeletePath_withInvalidPathId(){
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            pathService.deletePath(5L);
        });
        String expectedMessage = "No Path with provided id exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetPathsInArea() {

        Coordinate [] coordinatesArea = new Coordinate[5];
        coordinatesArea[0] = new Coordinate(8.432962, 47.378622);
        coordinatesArea[1] = new Coordinate(8.474358, 47.373311);
        coordinatesArea[2] = new Coordinate( 8.470429, 47.357797);
        coordinatesArea[3] = new Coordinate(8.437273, 47.362384);
        coordinatesArea[4] = new Coordinate(8.432962, 47.378622);
        Polygon polygon = geometryFactory.createPolygon(coordinatesArea);

        List<Path> pathsInArea = pathService.getAllPathsInArea(polygon);
        assertEquals(2, pathsInArea.size());
        assertEquals(1, pathsInArea.get(0).getId());
        assertEquals(4, pathsInArea.get(1).getId());
    }
}
