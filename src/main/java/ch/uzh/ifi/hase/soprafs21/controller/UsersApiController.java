package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ChatMessageDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ConversationDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.swagger.annotations.ApiParam;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(NativeWebRequest request, UserService userService, ChatService chatService) {
        this.request = request;
        this.userService = userService;
        this.chatService = chatService;
    }

    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Boolean> usersLoginPost(@RequestBody UserLoginDto userLoginDto) throws GeneralSecurityException, IOException {

        boolean isNewUser = true;
        System.out.println("tokenid:::::" + userLoginDto.getTokenId());
        System.out.println("email:::::" + userLoginDto.getEmailId());
        GoogleIdToken token = userService.authenticateTokenId(userLoginDto.getTokenId());


        if (null != token) {
            if (userService.verifyEmailIdForToken(token, userLoginDto.getEmailId())) {
                GoogleIdToken.Payload payload = token.getPayload();
                isNewUser= userService.loginOrRegisterUser(payload);

            }
        }else {
            System.out.println("Invalid ID token.");
            return new ResponseEntity<Boolean>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Boolean>(isNewUser,HttpStatus.OK);
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
        Point newLocation = new Point(coordinateDto.getLongitude(), coordinateDto.getLatitude());
        userService.updateUserLocation(userId, newLocation);
        return ResponseEntity.noContent().build();
    }
}
