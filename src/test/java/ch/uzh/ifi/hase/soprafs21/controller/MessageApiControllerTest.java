package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessagePostDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private User senderMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(authenticationMock.getPrincipal()).thenReturn("mark@twen.de");
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setEmail("mark@twen.de");
    }

    @Test
    void testPostMessageSuccess() throws Exception {
        ChatMessagePostDto message = new ChatMessagePostDto();
        message.setMessage("Hello");
        message.setReceiverId(2L);
        message.setSenderId(1L);

        when(userServiceMock.getUserById(eq(1L))).thenReturn(senderMock);
        when(userServiceMock.getUserById(eq(2L))).thenReturn(receiverMock);

        ResponseEntity<Void> responseEntity = messageApiController.sendMessage(message);
        verify(chatServiceMock).createMessage(eq(senderMock), eq(receiverMock), eq(message.getMessage()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}