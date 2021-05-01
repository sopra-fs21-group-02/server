package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    private GeometryFactory geometryFactory;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    @Mock
    private JwtTokenUtil jwtTokenUtilMock;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        userOverviewDto.setStatus(OnlineStatusDto.ONLINE);
        when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    void testUpdateUserLocation() {
        assertNull(userRepository.findById(4L).get().getLastUserLocation());
        Point newLocation = geometryFactory.createPoint(new Coordinate(47.35997146785179, 8.461859195948641));
        this.userService.updateUserLocation(1L, newLocation);

        assertNotNull(userRepository.findById(1L).get().getLastUserLocation());
        assertEquals(newLocation, userRepository.findById(1L).get().getLastUserLocation());
    }

    @Test
    void testGetUsersInArea() {
        Coordinate [] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(8.432962, 47.378622);
        coordinates[1] = new Coordinate(8.474358, 47.373311);
        coordinates[2] = new Coordinate( 8.470429, 47.357797);
        coordinates[3] = new Coordinate(8.437273, 47.362384);
        coordinates[4] = new Coordinate(8.432962, 47.378622);
        Polygon polygon = geometryFactory.createPolygon(coordinates);

        List<User> usersInArea = userService.getAllUsersInArea(polygon);
        assertEquals(2, usersInArea.size());
        assertEquals(1, usersInArea.get(0).getId());
        assertEquals(2, usersInArea.get(1).getId());
    }

    @Test
    void testGetUsers() {
        List<User> users = userService.getAllUsers();
        UserOverviewDto userOverview = (UserOverviewDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals(3, users.size());
        assertFalse(users.stream().anyMatch(user -> user.getId().equals(userOverview.getId())));
    }

    @Test
    void testLogoutUser(){
        this.userService.logoutUser(1L);
        assertNull(userRepository.findById(1L).get().getToken());
        assertEquals(OnlineStatus.OFFLINE, userRepository.findById(1L).get().getStatus());
    }

    @Test
    void testUpdateRefreshTokenForUser(){
        this.userService.updateRefreshTokenForUser("DUMMYTOKEN","mark@twen.de");
        assertEquals("DUMMYTOKEN", userRepository.findById(1L).get().getToken());
    }

    @Test
    void testGetUserDetails(){
        this.userService.getUserDetails(1L);
        assertEquals("Mark", userRepository.findById(1L).get().getName());
    }

    @Test
    void testGetUserById(){
        this.userService.getUserById(1L);
        assertEquals("Mark", userRepository.findById(1L).get().getName());
    }

    @Test
    void testLoginOrRegisterUser(){
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail("mark@twen.de");
        String refreshToken = "DUMMYTOKEN";

        this.userService.loginOrRegisterUser(payload, refreshToken);
        assertNotNull(userRepository.findByEmail(payload.getEmail()));
    }

    @Test
    void testRefreshToken(){
        String email = "mark5@twen.de";
        String token = jwtTokenUtil.generateRefreshToken(email);
        User mockedUser = User.builder().id(5L).email(email).name("Mark5").profilePictureURL("SomeURL").
                provider("SomeProvider").providerUid("SomeGoogleId").token(token).status(OnlineStatus.ONLINE).build();
        userRepository.saveAndFlush(mockedUser);
        this.userService.validateAndGetUserEmailFromRefreshToken(token);
    }

    @Test
    void testDeleteUser(){
        this.userService.deleteUser(4L);
        assertEquals(Optional.empty(),userRepository.findById(4L));
    }



}
