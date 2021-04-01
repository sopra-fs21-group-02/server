package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConversationDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
public class ConversationDto   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("participant")
  private UserDto participant;

  @JsonProperty("message")
  @Valid
  private List<ChatMessageDto> message = null;

  public ConversationDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(readOnly = true, value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ConversationDto participant(UserDto participant) {
    this.participant = participant;
    return this;
  }

  /**
   * Get participant
   * @return participant
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserDto getParticipant() {
    return participant;
  }

  public void setParticipant(UserDto participant) {
    this.participant = participant;
  }

  public ConversationDto message(List<ChatMessageDto> message) {
    this.message = message;
    return this;
  }

  public ConversationDto addMessageItem(ChatMessageDto messageItem) {
    if (this.message == null) {
      this.message = new ArrayList<>();
    }
    this.message.add(messageItem);
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ChatMessageDto> getMessage() {
    return message;
  }

  public void setMessage(List<ChatMessageDto> message) {
    this.message = message;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationDto conversation = (ConversationDto) o;
    return Objects.equals(this.id, conversation.id) &&
        Objects.equals(this.participant, conversation.participant) &&
        Objects.equals(this.message, conversation.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, participant, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    participant: ").append(toIndentedString(participant)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

