package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.LoginPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
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

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean getUserDetails(@RequestBody LoginPostDTO loginPostDTO) throws GeneralSecurityException, IOException {

        boolean isNewUser = true;
        System.out.println("tokenid:::::" + loginPostDTO.getTokenId());
        GoogleIdToken token = userService.authenticateTokenId(loginPostDTO.getTokenId());


        if (null != token) {
            if (userService.verifyEmailIdForToken(token, loginPostDTO.getEmailId())) {
                GoogleIdToken.Payload payload = token.getPayload();
                isNewUser= userService.loginOrRegisterUser(payload);

            }
        }else {
            System.out.println("Invalid ID token.");
            return false;
        }
        return isNewUser;
    }

}
