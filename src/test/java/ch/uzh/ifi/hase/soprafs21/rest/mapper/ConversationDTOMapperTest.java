package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConversationDTOMapperTest {

    @Test
    void testToDTO() {
        User sender = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();
        User receiver = User.builder().id(2l).email("email2").name("name2").profilePictureURL("url2").build();

        ChatMessage chatMessage1 = ChatMessage.builder()
                .id(1l)
                .sender(sender)
                .receiver(receiver)
                .unread(true)
                .message("something")
                .timeStamp(LocalDateTime.now()).build();
        ChatMessage chatMessage2 = ChatMessage.builder()
                .id(2l)
                .sender(receiver)
                .receiver(sender)
                .unread(true)
                .message("lastMessage")
                .timeStamp(LocalDateTime.now()).build();
        Conversation conversation = Conversation.builder()
                .id(1l)
                .participant1(sender)
                .participant2(receiver)
                .messages(Arrays.asList(chatMessage1, chatMessage2)).build();

        ConversationDto dto = ConversationDTOMapper.INSTANCE.toDTO(conversation);
        assertEquals(conversation.getParticipant2().getId(), dto.getParticipant().getId());
        assertEquals(conversation.getMessages().get(0).getId(), dto.getLastMessage().getId());
    }
}