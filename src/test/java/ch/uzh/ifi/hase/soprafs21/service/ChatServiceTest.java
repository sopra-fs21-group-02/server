package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.ChatMessageRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ConversationRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Sql(value = {"/data_init.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    private User sender,receiver;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(authenticationMock.getPrincipal()).thenReturn("mark@twen.de");
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
        sender = userRepository.findById(1L).orElseThrow();
        receiver = userRepository.findById(2L).orElseThrow();
    }

    @Test
    void getAllConversations() {
        List<Conversation> conversations = chatService.getAllConversations(1l);
        assertEquals(2, conversations.size());
        assertEquals(5, conversations.get(0).getMessages().size());
        LocalDateTime currentDateTime = LocalDateTime.now();

        for(ChatMessage chatMessage: conversations.get(0).getMessages()) {
            assertTrue(currentDateTime.isAfter(chatMessage.getTimeStamp()));
            currentDateTime = chatMessage.getTimeStamp();
        }
    }

    @Test
    void getALLMessagesByConversation() {
        List<ChatMessage> messages = chatService.getAllMessages(sender, receiver);

        assertEquals(5, messages.size());

        LocalDateTime currentDateTime = LocalDateTime.now();
        for(ChatMessage chatMessage: messages) {
            assertTrue(currentDateTime.isAfter(chatMessage.getTimeStamp()));
            assertTrue(chatMessage.isUnread());
            currentDateTime = chatMessage.getTimeStamp();
        }
        messages = chatService.getAllMessages(sender, receiver);
        for(ChatMessage chatMessage: messages) {
            assertFalse(chatMessage.isUnread());
        }
    }

    @Test
    void createMessageExistingConversation() {
        chatService.createMessage(sender, receiver, "newMessage");
        List<ChatMessage> messages = chatService.getAllMessages(sender, receiver);

        assertEquals(6, messages.size());
    }

    @Test
    void createMessageNewConversation() {
        User newParticipant = userRepository.findById(3L).orElseThrow();
        chatService.createMessage(sender, newParticipant, "newMessage");
        List<ChatMessage> messages = chatService.getAllMessages(sender, newParticipant);

        assertEquals(1, messages.size());
        assertEquals("mark@twen.de", messages.get(0).getConversation().getParticipant1().getEmail());
        assertEquals(newParticipant.getEmail(), messages.get(0).getConversation().getParticipant2().getEmail());
    }
}