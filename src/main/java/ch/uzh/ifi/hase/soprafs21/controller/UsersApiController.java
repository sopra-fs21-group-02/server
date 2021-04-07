package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginPostDto;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(NativeWebRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    private final UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<UserLoginPostDto> usersLoginPost(@RequestBody UserLoginDto userLoginDto) throws GeneralSecurityException, IOException {

        boolean isNewUser = true;
        String jwtToken =null;
        GoogleIdToken token = userService.authenticateTokenId(userLoginDto.getTokenId());

        if (null != token) {
            if (userService.verifyEmailIdForToken(token, userLoginDto.getEmailId())) {
                GoogleIdToken.Payload payload = token.getPayload();
                isNewUser= userService.loginOrRegisterUser(payload);
                jwtToken = jwtTokenUtil.generateToken(userLoginDto.getEmailId());
            }
        }else {
            return new ResponseEntity<UserLoginPostDto>(HttpStatus.CONFLICT);
        }

        UserLoginPostDto userLoginPostDto = new UserLoginPostDto();
        userLoginPostDto.setIsNewUser(isNewUser);
        userLoginPostDto.setJwtToken(jwtToken);
        return new ResponseEntity<UserLoginPostDto>(userLoginPostDto, HttpStatus.OK);
    }



}
