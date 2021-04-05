package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ChatMessageDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ConversationDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

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
    public ResponseEntity<List<ChatMessageDto>> usersUserId1ConversationsUserId2Get(@ApiParam(value = "Numeric ID of the user1",required=true) @PathVariable("userId1") Long userId1, @ApiParam(value = "Numeric ID of the user2",required=true) @PathVariable("userId2") Long userId2) throws Exception {
        if (!isRequesterAndAuthenticatedUserTheSame(userId1)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User receiver = userService.getUserById(userId2);
        try {
            List<ChatMessage> messages = chatService.getAllMessages(receiver);
            return ResponseEntity.ok(ChatMessageDTOMapper.INSTANCE.toDTO(messages));
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isRequesterAndAuthenticatedUserTheSame(Long userId1) {
        String authenticatedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.getUserByEmail(authenticatedUserEmail).orElseThrow();
        User sender = userService.getUserById(userId1);
        return authenticatedUser.equals(sender);
    }

    @Override
    public ResponseEntity<List<ConversationDto>> usersUserIdConversationsGet(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId) throws Exception {
        if (!isRequesterAndAuthenticatedUserTheSame(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User sender = userService.getUserById(userId);
        try {
            List<Conversation> conversations = chatService.getAllConversations();
            return ResponseEntity.ok(ConversationDTOMapper.INSTANCE.toDTO(conversations));
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }


}
