package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ChatMessagePostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ChatMessagePostDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("senderId")
  private Long senderId;

  @JsonProperty("receiverId")
  private Long receiverId;

  @JsonProperty("message")
  private String message;

  public ChatMessagePostDto senderId(Long senderId) {
    this.senderId = senderId;
    return this;
  }

  /**
   * Get senderId
   * @return senderId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public ChatMessagePostDto receiverId(Long receiverId) {
    this.receiverId = receiverId;
    return this;
  }

  /**
   * Get receiverId
   * @return receiverId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public ChatMessagePostDto message(String message) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatMessagePostDto chatMessagePost = (ChatMessagePostDto) o;
    return Objects.equals(this.senderId, chatMessagePost.senderId) &&
        Objects.equals(this.receiverId, chatMessagePost.receiverId) &&
        Objects.equals(this.message, chatMessagePost.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(senderId, receiverId, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatMessagePostDto {\n");
    
    sb.append("    senderId: ").append(toIndentedString(senderId)).append("\n");
    sb.append("    receiverId: ").append(toIndentedString(receiverId)).append("\n");
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

