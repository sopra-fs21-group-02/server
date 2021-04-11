package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.ChatMessage;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(uses = {UserDTOMapper.class})
public interface ChatMessageDTOMapper {

    ChatMessageDTOMapper INSTANCE = Mappers.getMapper(ChatMessageDTOMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "message", target = "message")
    @Mapping(expression = "java(INSTANCE.convertToOffsetDateTime(entity.getTimeStamp()))", target = "timeStamp")
    @Mapping(source = "unread", target = "unread")
    ChatMessageDto toDTO(ChatMessage entity);

    List<ChatMessageDto> toDTO(List<ChatMessage> entities);

    default OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
    }
}
