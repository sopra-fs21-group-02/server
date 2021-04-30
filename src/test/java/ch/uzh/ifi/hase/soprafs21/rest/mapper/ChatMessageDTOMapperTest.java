package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class ChatMessageDTOMapperTest {
    @Test
    public void testToDTO() {
        User sender = User.builder().id(1l).email("email1").name("name1").profilePictureURL("url1").build();
        User receiver = User.builder().id(2l).email("email2").name("name2").profilePictureURL("url2").build();

        ChatMessage chatMessage = ChatMessage.builder()
                .id(1l)
                .sender(sender)
                .receiver(receiver)
                .unread(true)
                .message("something")
                .timeStamp(LocalDateTime.now()).build();
        ChatMessageDto dto = ChatMessageDTOMapper.INSTANCE.toDTO(chatMessage);
        assertEquals(chatMessage.getId(), dto.getId());
        assertEquals(chatMessage.isUnread(), dto.getUnread());
        assertEquals(chatMessage.getMessage(), dto.getMessage());
        assertEquals(ChatMessageDTOMapper.INSTANCE.convertToOffsetDateTime(chatMessage.getTimeStamp()), dto.getTimeStamp());
        assertNotNull(dto.getSender());
        assertNotNull(dto.getReceiver());
    }
}
