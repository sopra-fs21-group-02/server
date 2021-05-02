package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.entity.Conversation;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserDTOMapper.class})
public interface ConversationDTOMapper {
    ConversationDTOMapper INSTANCE = Mappers.getMapper(ConversationDTOMapper.class);

    @Mapping(source = "participant2", target = "participant")
    @Mapping(expression = "java(getLastMessageDTO(entity.getMessages()))", target = "lastMessage")
    ConversationDto toDTO(Conversation entity);

    List<ConversationDto> toDTO(List<Conversation> entities);

    default ChatMessageDto getLastMessageDTO(List<ChatMessage> messages) {
        return ChatMessageDTOMapper.INSTANCE.toDTO(messages.get(0));
    }
}
