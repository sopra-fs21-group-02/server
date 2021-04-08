package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ChatMessageDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ChatMessageDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("sender")
  private UserOverviewDto sender;

  @JsonProperty("receiver")
  private UserOverviewDto receiver;

  @JsonProperty("message")
  private String message;

  @JsonProperty("timeStamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timeStamp;

  @JsonProperty("unread")
  private Boolean unread;

  public ChatMessageDto id(Long id) {
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

  public ChatMessageDto sender(UserOverviewDto sender) {
    this.sender = sender;
    return this;
  }

  /**
   * Get sender
   * @return sender
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UserOverviewDto getSender() {
    return sender;
  }

  public void setSender(UserOverviewDto sender) {
    this.sender = sender;
  }

  public ChatMessageDto receiver(UserOverviewDto receiver) {
    this.receiver = receiver;
    return this;
  }

  /**
   * Get receiver
   * @return receiver
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UserOverviewDto getReceiver() {
    return receiver;
  }

  public void setReceiver(UserOverviewDto receiver) {
    this.receiver = receiver;
  }

  public ChatMessageDto message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ChatMessageDto timeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  /**
   * Get timeStamp
   * @return timeStamp
  */
  @ApiModelProperty(readOnly = true, value = "")

  @Valid

  public OffsetDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public ChatMessageDto unread(Boolean unread) {
    this.unread = unread;
    return this;
  }

  /**
   * Get unread
   * @return unread
  */
  @ApiModelProperty(readOnly = true, value = "")


  public Boolean getUnread() {
    return unread;
  }

  public void setUnread(Boolean unread) {
    this.unread = unread;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatMessageDto chatMessage = (ChatMessageDto) o;
    return Objects.equals(this.id, chatMessage.id) &&
        Objects.equals(this.sender, chatMessage.sender) &&
        Objects.equals(this.receiver, chatMessage.receiver) &&
        Objects.equals(this.message, chatMessage.message) &&
        Objects.equals(this.timeStamp, chatMessage.timeStamp) &&
        Objects.equals(this.unread, chatMessage.unread);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sender, receiver, message, timeStamp, unread);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatMessageDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    receiver: ").append(toIndentedString(receiver)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
    sb.append("    unread: ").append(toIndentedString(unread)).append("\n");
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

