package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ChatMessageDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ConversationDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiParam;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZoneOffset;
import java.util.*;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(NativeWebRequest request, UserService userService, ChatService chatService, JwtTokenUtil jwtTokenUtil) {
        this.request = request;
        this.userService = userService;
        this.chatService = chatService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private final UserService userService;
    private final ChatService chatService;
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<UserLoginPostDto> usersLoginPost(@RequestBody UserLoginDto userLoginDto) throws GeneralSecurityException, IOException {

        boolean isNewUser = true;
        String accessToken =null;
        String refreshToken = null;
        Date accessTokenExpiry = null;
        GoogleIdToken token = userService.authenticateTokenId(userLoginDto.getTokenId());
        ResponseCookie refreshTokenCookie =null;

        if (null != token) {
            if (userService.verifyEmailIdForToken(token, userLoginDto.getEmailId())) {

                //generation of access token
                accessToken = jwtTokenUtil.generateToken(userLoginDto.getEmailId());
                //get expiry time of access token
                accessTokenExpiry = jwtTokenUtil.getExpirationTimeForAccessToken(accessToken);
                //generate refresh token
                refreshToken = jwtTokenUtil.generateRefreshToken(userLoginDto.getEmailId());
                //call method to save or update user in database
                isNewUser= userService.loginOrRegisterUser(token.getPayload(), refreshToken);
                //create cookie to hold refresh token
                refreshTokenCookie = ResponseCookie.from("refresh_token",refreshToken)
                        .httpOnly(true)
                        .secure(true)
                        .maxAge(SecurityConstants.REFRESH_EXPIRATION_TIME/1000) //convert expiry time from ms to sec
                        .build();

            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        //response object
        UserLoginPostDto userLoginPostDto = new UserLoginPostDto();
        userLoginPostDto.setIsNewUser(isNewUser);
        userLoginPostDto.setAccessToken(accessToken);
        userLoginPostDto.setAccessTokenExpiry(accessTokenExpiry.toInstant().atOffset(ZoneOffset.ofHours(2)));

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(userLoginPostDto);
    }

    @Override
    public ResponseEntity<List<ChatMessageDto>> getAllMessages(@ApiParam(value = "Numeric ID of the user1",required=true) @PathVariable("userId1") Long userId1, @ApiParam(value = "Numeric ID of the user2",required=true) @PathVariable("userId2") Long userId2) throws Exception {
        User receiver = userService.getUserById(userId2);
        User sender = userService.getUserById(userId1);
        try {
            List<ChatMessage> messages = chatService.getAllMessages(sender, receiver);
            return ResponseEntity.ok(ChatMessageDTOMapper.INSTANCE.toDTO(messages));
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<ConversationDto>> getAllConversations(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId) throws Exception {
        User sender = userService.getUserById(userId);
        try {
            List<Conversation> conversations = chatService.getAllConversations(userId);
            return ResponseEntity.ok(ConversationDTOMapper.INSTANCE.toDTO(conversations));
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> updateLocation(@ApiParam(value = "Numeric ID of the user to update",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Coordinate object that needs to be updated in user with userId" ,required=true )  @Valid @RequestBody CoordinateDto coordinateDto) throws Exception {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(coordinateDto.getLongitude(), coordinateDto.getLatitude()));
        userService.updateUserLocation(userId, newLocation);
        return ResponseEntity.noContent().build();
    }
    @Override
    public ResponseEntity<UserLoginPostDto> usersRefreshTokenPut(@NotNull String refreshToken) throws Exception {

        String newAccessToken =null;
        String newRefreshToken = null;
        Date accessTokenExpiry = null;
        ResponseCookie newRefreshTokenCookie =null;
        UserLoginPostDto userLoginPostDto = new UserLoginPostDto();

        String validatedUserEmailId = userService.refreshToken(refreshToken);

        //generation of access token
        newAccessToken = jwtTokenUtil.generateToken(validatedUserEmailId);
        //get expiry time of access token
        accessTokenExpiry = jwtTokenUtil.getExpirationTimeForAccessToken(newAccessToken);
        //generate refresh token
        newRefreshToken = jwtTokenUtil.generateRefreshToken(validatedUserEmailId);

        userService.updateRefreshTokenForUser(newRefreshToken, validatedUserEmailId);

        userLoginPostDto.setAccessToken(newAccessToken);
        userLoginPostDto.setAccessTokenExpiry(accessTokenExpiry.toInstant().atOffset(ZoneOffset.ofHours(2)));
        userLoginPostDto.setIsNewUser(Boolean.FALSE);
        //create cookie to hold refresh token
        newRefreshTokenCookie = ResponseCookie.from("refresh_token",refreshToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(SecurityConstants.REFRESH_EXPIRATION_TIME/1000) //convert expiry time from ms to sec
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, newRefreshTokenCookie.toString())
                .body(userLoginPostDto);
    }

    @Override
    public ResponseEntity<UserDto> usersUserIdGet(Long userId) throws Exception {
        UserDto userDto = null;
        if (userId != null) {
            userDto = userService.getUserDetails(userId);
            if(null == userDto){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid input userId cannot be null");
        }
        return ResponseEntity
                .ok()
                .body(userDto);
    }
}
