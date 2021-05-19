package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.repository.ParkRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
public class ParkServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private GeometryFactory geometryFactory;

    @Autowired
    private ParkService parkService;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    private Park park;

    @BeforeEach
    void setUp() {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        Coordinate coordinate = new Coordinate(8.432962, 47.378622);
        Point location = geometryFactory.createPoint(coordinate);
        park = Park.builder().coordinate(location).description("Some description").creator(userRepository.getOne(1L)).build();
    }

    @Test
    void testAddPark() {
        Park persistedPark = parkService.addPark(park);

        assertEquals(park.getDescription(), persistedPark.getDescription());
        assertEquals(park.getCreator().getId(), persistedPark.getCreator().getId());
        assertEquals(park.getCoordinate().getX(), persistedPark.getCoordinate().getX());
        assertEquals(park.getCoordinate().getY(), persistedPark.getCoordinate().getY());
    }

    @Test
    void testAddParkUnauthorizedUser() {
        park.setCreator(userRepository.getOne(3L));

        Exception ex = assertThrows(ResponseStatusException.class, () -> parkService.addPark(park));

        String expectedMessage = "User is not permitted to add another user parks";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetParksInArea() {
        Coordinate coordinate = new Coordinate(8.451911, 47.371412);
        Point location = geometryFactory.createPoint(coordinate);
        Park park1 = Park.builder().coordinate(location).creator(userRepository.getOne(1L)).build();
        parkRepository.saveAndFlush(park1);

        coordinate = new Coordinate(8.457969, 47.36495);
        location = geometryFactory.createPoint(coordinate);
        Park park2 = Park.builder().coordinate(location).creator(userRepository.getOne(3L)).build();
        parkRepository.saveAndFlush(park2);

        Coordinate [] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(8.432962, 47.378622);
        coordinates[1] = new Coordinate(8.474358, 47.373311);
        coordinates[2] = new Coordinate( 8.470429, 47.357797);
        coordinates[3] = new Coordinate(8.437273, 47.362384);
        coordinates[4] = new Coordinate(8.432962, 47.378622);
        Polygon polygon = geometryFactory.createPolygon(coordinates);

        List<Park> parksInArea = parkService.getAllParksInArea(polygon);
        assertEquals(2, parksInArea.size());
        assertEquals(1, parksInArea.get(0).getCreator().getId());
        assertEquals(3, parksInArea.get(1).getCreator().getId());
    }

    @Test
    void testDeleteParkSuccess() {
        Park persistedPark = parkService.addPark(park);

        parkService.deletePark(persistedPark.getId());
        Optional<Park> notExistingPark = parkRepository.findById(persistedPark.getId());
        assertTrue(notExistingPark.isEmpty());
    }

    @Test
    void testDeleteNotExistingPark() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> parkService.deletePark(66L));

        String expectedMessage = "No Park with provided id exists";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteParkOfAnotherUser() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> parkService.deletePark(1L));

        String expectedMessage = "User is not permitted to delete another user parks";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
