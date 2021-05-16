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
        this.pathService.deletePath(1L, persistedPath.getId());
        assertEquals(Optional.empty(), pathRepository.findById(persistedPath.getId()));
    }

    @Test
    void testDeletePath_withUnauthenticateUser(){
        Path persistedPath = pathService.savePath(path);
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            pathService.deletePath(2L, persistedPath.getId());
        });

        String expectedMessage = "User is not permitted to delete this Route";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeletePath_withInvalidPathId(){
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            pathService.deletePath(2L, 5L);
        });
        String expectedMessage = "Invalid Path id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
