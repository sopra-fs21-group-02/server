package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.UserDTOMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.ArrayList;
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
    void getUserSuccess() throws Exception {

        User mockedUser = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();

        given(userServiceMock.getUserDetails(1L)).willReturn(mockedUser);

        ResponseEntity<UserDto> responseEntity = usersApiController.usersUserIdGet(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedUser.getEmail() ,responseEntity.getBody().getEmail());
        assertEquals(mockedUser.getId(),responseEntity.getBody().getId());
        assertEquals(mockedUser.getName(),responseEntity.getBody().getName());
        assertEquals(mockedUser.getProfilePictureURL(),responseEntity.getBody().getProfilePicture());

    }

    @Test
    void getUserNotFound() throws Exception {
        UserDto mockedUserDto = getMockedUserDto();

        given(userServiceMock.getUserDetails(Mockito.any())).willReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            usersApiController.usersUserIdGet(1L);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private UserDto getMockedUserDto() {
        UserDto mockedDto = new UserDto();
        mockedDto.setId(1L);
        mockedDto.setName("mockName");
        mockedDto.setEmail("mock@gmail.com");
        mockedDto.setStatus(OnlineStatusDto.ONLINE);

        DogDto d1 = new DogDto();
        d1.setName("D1");
        d1.setBreed("B1");
        d1.setSex(GenderDto.MALE);

        DogDto d2 = new DogDto();
        d2.setName("D2");
        d2.setBreed("B2");
        d2.setSex(GenderDto.FEMALE);

        List<DogDto> dogList = new ArrayList<DogDto>();

        dogList.add(d1);
        dogList.add(d2);
        mockedDto.setDogs(dogList);

        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLongitude(47.35997146785179);
        coordinateDto.setLongitude(8.461859195948641);

        mockedDto.setLatestLocation(coordinateDto);
        return mockedDto;
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

    @Test
    public void testAddDogWithId() {
        DogDto dogDto = new DogDto();
        dogDto.setName("D1");
        dogDto.setBreed("B1");
        dogDto.setSex(GenderDto.MALE);
        dogDto.setId(1L);

        assertThrows(ResponseStatusException.class, () -> usersApiController.addDog(1L, dogDto, null));
    }

}
