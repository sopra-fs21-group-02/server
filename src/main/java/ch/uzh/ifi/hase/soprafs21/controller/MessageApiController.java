package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ConversationDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.UserDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class MessageApiController implements MessageApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public MessageApiController(NativeWebRequest request, UserService userService, ChatService chatService) {
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
    public ResponseEntity<Void> messagePost(@ApiParam(value = "A message that needs to be created" ,required=true )  @Valid @RequestBody ChatMessageDto chatMessageDto) throws Exception {
        Long senderId = chatMessageDto.getSender().getId();
        Long receiverId = chatMessageDto.getReceiver().getId();

        String authenticatedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.getUserByEmail(authenticatedUserEmail).orElseThrow();
        User sender = userService.getUserById(senderId);
        if (!authenticatedUser.equals(sender)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User receiver = userService.getUserById(receiverId);
        chatService.createMessage(receiver, chatMessageDto.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
