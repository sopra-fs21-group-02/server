package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ChatMessageDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.ChatService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageApiControllerTest {

    @Mock
    private ChatService chatServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private User receiverMock;

    @InjectMocks
    private MessageApiController messageApiController;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    private User authenticatedUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(authenticationMock.getPrincipal()).thenReturn("mark@twen.de");
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        authenticatedUser = new User();
        authenticatedUser.setId(1l);
        authenticatedUser.setEmail("mark@twen.de");
    }

    @Test
    void testPostMessageSuccess() throws Exception {
        UserOverviewDto receiver = new UserOverviewDto();
        receiver.setId(2l);
        UserOverviewDto sender = new UserOverviewDto();
        sender.setId(1l);
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(1l))).thenReturn(authenticatedUser);
        when(this.userServiceMock.getUserById(eq(2l))).thenReturn(receiverMock);
        ChatMessageDto message = new ChatMessageDto();
        message.setMessage("Hello");
        message.setReceiver(receiver);
        message.setSender(sender);

        ResponseEntity<Void> responseEntity = messageApiController.messagePost(message);
        verify(chatServiceMock).createMessage(eq(receiverMock), eq(message.getMessage()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}