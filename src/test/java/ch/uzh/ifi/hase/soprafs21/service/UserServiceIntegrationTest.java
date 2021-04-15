package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@SpringBootTest
@Sql(value = {"/data_init.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testUpdateUserLocation() {
        assertNull(userRepository.findById(1l).get().getLastUserLocation());
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(47.35997146785179, 8.461859195948641));
        this.userService.updateUserLocation(1l, newLocation);

        assertNotNull(userRepository.findById(1l).get().getLastUserLocation());
        assertEquals(newLocation, userRepository.findById(1l).get().getLastUserLocation());
    }
}
