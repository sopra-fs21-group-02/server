package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.webtoken.JsonWebSignature;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class UsersApiControllerTest {

    @InjectMocks
    private UsersApiController usersApiController;

    @Mock
    private UserService userServiceMock;

    @Mock
    private GeometryFactory geometryFactoryMock;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private User user1;

    @Mock
    private User user2;

    @Mock
    private Polygon polygon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        //jwtTokenUtil = new JwtTokenUtil();
    }

    @Test
    void usersLoginPostSuccess() throws GeneralSecurityException, IOException {

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmailId("mock@gmail.com");
        userLoginDto.setTokenId("mockTokenId");

        var header = new JsonWebSignature.Header().setAlgorithm("RS256");
        var payload = new GoogleIdToken.Payload();
        GoogleIdToken mockGoogleIdToken = new GoogleIdToken(header, payload,new byte[10],new byte[10]);

        String mockAccessToken = "mockAccessToken";
        String mockRefreshToken = "mockRefreshToken";

        given(userServiceMock.authenticateTokenId(userLoginDto.getTokenId())).willReturn(mockGoogleIdToken);
        given(userServiceMock.verifyEmailIdForToken(Mockito.any(), Mockito.any())).willReturn(Boolean.TRUE);
        given(jwtTokenUtil.generateToken(Mockito.any())).willReturn(mockAccessToken);
        given(jwtTokenUtil.getExpirationTimeForAccessToken(Mockito.any())).willReturn(new Date());
        given(jwtTokenUtil.generateRefreshToken(Mockito.any())).willReturn(mockRefreshToken);

        ResponseEntity<UserLoginPostDto> responseEntity = usersApiController.usersLoginPost(userLoginDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().get("Set-Cookie").get(0).contains("mockRefreshToken"));
        assertEquals(mockAccessToken, responseEntity.getBody().getAccessToken());
    }

    @Test
    void refreshToken() throws Exception {
        String mockAccessToken = "mockAccessToken";
        String mockRefreshToken = "mockRefreshToken";
        String mockEmailId = "mock@gmail.com";

        given(userServiceMock.refreshToken(mockRefreshToken)).willReturn(mockEmailId);
        given(jwtTokenUtil.generateToken(Mockito.any())).willReturn(mockAccessToken);
        given(jwtTokenUtil.getExpirationTimeForAccessToken(Mockito.any())).willReturn(new Date());
        given(jwtTokenUtil.generateRefreshToken(Mockito.any())).willReturn(mockRefreshToken);

        ResponseEntity<UserLoginPostDto> responseEntity = usersApiController.usersRefreshTokenPut(mockRefreshToken);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().get("Set-Cookie").get(0).contains("mockRefreshToken"));
        assertEquals(mockAccessToken, responseEntity.getBody().getAccessToken());
    }

    @Test
    void logoutSuccess () throws Exception{
        Long mockUserId = 1L;
        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(mockUserId)).willReturn(Boolean.TRUE);

        ResponseEntity<Void> responseEntity = usersApiController.usersUserIdLogoutPut(mockUserId);
        verify(userServiceMock).logoutUser(eq(mockUserId));
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void logoutForbidden () throws Exception{
        Long mockUserId = 1L;
        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(mockUserId)).willReturn(Boolean.FALSE);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            usersApiController.usersUserIdLogoutPut(mockUserId);
        });

        String expectedMessage = "Do not have permission to logout other user";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void logoutNullObject () throws Exception{

        given(userServiceMock.isRequesterAndAuthenticatedUserTheSame(null)).willReturn(Boolean.FALSE);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            usersApiController.usersUserIdLogoutPut(null);
        });

        String expectedMessage = "Invalid input userId cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getUsersWithoutParam() throws Exception {
        List<User> listUsers = Arrays.asList(user1, user2);
        when(userServiceMock.getAllUsers()).thenReturn(listUsers);
        ResponseEntity<List<UserOverviewDto>> entity = usersApiController.getUsers(null, null);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(2, entity.getBody().size());
    }

    @Test
    void getUsersByArea() throws Exception {
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

        List<User> listUsers = Arrays.asList(user1, user2);
        when(userServiceMock.getAllUsersInArea(any())).thenReturn(listUsers);

        ResponseEntity<List<UserOverviewDto>> entity = usersApiController.getUsers(areaFilter, null);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(2, entity.getBody().size());
    }

}