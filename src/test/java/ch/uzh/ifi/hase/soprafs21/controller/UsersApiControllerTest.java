package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.webtoken.JsonWebSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class UsersApiControllerTest {

    @InjectMocks
    private UsersApiController usersApiController;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

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

        given(userService.authenticateTokenId(userLoginDto.getTokenId())).willReturn(mockGoogleIdToken);
        given(userService.verifyEmailIdForToken(Mockito.any(), Mockito.any())).willReturn(Boolean.TRUE);
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

        given(userService.refreshToken(mockRefreshToken)).willReturn(mockEmailId);
        given(jwtTokenUtil.generateToken(Mockito.any())).willReturn(mockAccessToken);
        given(jwtTokenUtil.getExpirationTimeForAccessToken(Mockito.any())).willReturn(new Date());
        given(jwtTokenUtil.generateRefreshToken(Mockito.any())).willReturn(mockRefreshToken);

        ResponseEntity<UserLoginPostDto> responseEntity = usersApiController.usersRefreshTokenPut(mockRefreshToken);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().get("Set-Cookie").get(0).contains("mockRefreshToken"));
        assertEquals(mockAccessToken, responseEntity.getBody().getAccessToken());
    }

    @Test
    void getUserSuccess() throws Exception {
        UserDto mockedUserDto = getMockedUserDto();
        given(userService.getUserDetails(Mockito.any())).willReturn(mockedUserDto);
        ResponseEntity<UserDto> responseEntity = usersApiController.usersUserIdGet(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedUserDto,responseEntity.getBody());
    }

    @Test
    void getUserNotFound() throws Exception {
        UserDto mockedUserDto = getMockedUserDto();

        given(userService.getUserDetails(Mockito.any())).willReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            usersApiController.usersUserIdGet(1L);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getUserNullId() throws Exception {
        UserDto mockedUserDto = getMockedUserDto();

        given(userService.getUserDetails(Mockito.any())).willReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            usersApiController.usersUserIdGet(null);
        });

        String expectedMessage = "Invalid input userId cannot be null";
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
}