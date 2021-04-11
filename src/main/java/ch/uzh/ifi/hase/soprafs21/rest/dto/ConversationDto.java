package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConversationDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ConversationDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("participant")
  private UserOverviewDto participant;

  @JsonProperty("lastMessage")
  private ChatMessageDto lastMessage;

  public ConversationDto participant(UserOverviewDto participant) {
    this.participant = participant;
    return this;
  }

  /**
   * Get participant
   * @return participant
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserOverviewDto getParticipant() {
    return participant;
  }

  public void setParticipant(UserOverviewDto participant) {
    this.participant = participant;
  }

  public ConversationDto lastMessage(ChatMessageDto lastMessage) {
    this.lastMessage = lastMessage;
    return this;
  }

  /**
   * Get lastMessage
   * @return lastMessage
  */
  @ApiModelProperty(value = "")

  @Valid

  public ChatMessageDto getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(ChatMessageDto lastMessage) {
    this.lastMessage = lastMessage;
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
    return Objects.equals(this.participant, conversation.participant) &&
        Objects.equals(this.lastMessage, conversation.lastMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(participant, lastMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationDto {\n");
    
    sb.append("    participant: ").append(toIndentedString(participant)).append("\n");
    sb.append("    lastMessage: ").append(toIndentedString(lastMessage)).append("\n");
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

