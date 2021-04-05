package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ConversationDTOMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

class UsersApiControllerTest {

    @Mock
    private ChatService chatServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    @InjectMocks
    private UsersApiController usersApiController;

    private User authenticatedUser;

    @Mock
    private User receiverMock;

    @Mock
    private User senderMock;

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
    void testGetExistingConversationMessages() throws Exception {
        ChatMessage message1 = ChatMessage.builder().timeStamp(LocalDateTime.now()).build();
        ChatMessage message2 = ChatMessage.builder().timeStamp(LocalDateTime.now()).build();
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(1l))).thenReturn(authenticatedUser);
        when(this.userServiceMock.getUserById(eq(2l))).thenReturn(receiverMock);
        when(this.chatServiceMock.getAllMessages(eq(receiverMock))).thenReturn(Arrays.asList(message1, message2));

        ResponseEntity<List<ChatMessageDto>> responseEntity = usersApiController.usersUserId1ConversationsUserId2Get(1l, 2l);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void testGetMessagesNonExistingConversation() throws Exception {
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(1l))).thenReturn(authenticatedUser);
        when(this.userServiceMock.getUserById(eq(3l))).thenReturn(receiverMock);
        when(this.chatServiceMock.getAllMessages(eq(receiverMock))).thenThrow(new NoSuchElementException());
        ResponseEntity<List<ChatMessageDto>> responseEntity = usersApiController.usersUserId1ConversationsUserId2Get(1l, 3l);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetMessagesUnauthorised() throws Exception {
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(3l))).thenReturn(senderMock);
        when(this.userServiceMock.getUserById(eq(2l))).thenReturn(receiverMock);
        ResponseEntity<List<ChatMessageDto>> responseEntity = usersApiController.usersUserId1ConversationsUserId2Get(2l, 3l);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void testGetExistingConversations() throws Exception {
        List<ChatMessage> messages = Arrays.asList(ChatMessage.builder().timeStamp(LocalDateTime.now()).build());
        Conversation conversation1 = Conversation.builder().messages(messages).build();
        Conversation conversation2 = Conversation.builder().messages(messages).build();
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(1l))).thenReturn(authenticatedUser);
        when(this.userServiceMock.getUserById(eq(2l))).thenReturn(receiverMock);
        when(this.chatServiceMock.getAllConversations()).thenReturn(Arrays.asList(conversation1, conversation2));

        ResponseEntity<List<ConversationDto>> responseEntity = usersApiController.usersUserIdConversationsGet(1l);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void testGetConversationsUnauthorised() throws Exception {
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(3l))).thenReturn(senderMock);
        when(this.userServiceMock.getUserById(eq(2l))).thenReturn(receiverMock);
        ResponseEntity<List<ConversationDto>> responseEntity = usersApiController.usersUserIdConversationsGet(3l);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void testGetNonExistingConversations() throws Exception {
        when(this.userServiceMock.getUserByEmail(eq("mark@twen.de"))).thenReturn(Optional.of(authenticatedUser));
        when(this.userServiceMock.getUserById(eq(1l))).thenReturn(authenticatedUser);
        when(this.userServiceMock.getUserById(eq(3l))).thenReturn(receiverMock);
        when(this.chatServiceMock.getAllConversations()).thenThrow(new NoSuchElementException());
        ResponseEntity<List<ConversationDto>> responseEntity = usersApiController.usersUserIdConversationsGet(1l);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}