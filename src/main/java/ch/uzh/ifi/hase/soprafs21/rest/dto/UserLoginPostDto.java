package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
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
 * UserLoginPostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserLoginPostDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("accessToken")
  private String accessToken;

  @JsonProperty("accessTokenExpiry")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime accessTokenExpiry;

  @JsonProperty("isNewUser")
  private Boolean isNewUser;

  @JsonProperty("userId")
  private Long userId;

  public UserLoginPostDto accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * Get accessToken
   * @return accessToken
  */
  @ApiModelProperty(value = "")


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public UserLoginPostDto accessTokenExpiry(OffsetDateTime accessTokenExpiry) {
    this.accessTokenExpiry = accessTokenExpiry;
    return this;
  }

  /**
   * Get accessTokenExpiry
   * @return accessTokenExpiry
  */
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getAccessTokenExpiry() {
    return accessTokenExpiry;
  }

  public void setAccessTokenExpiry(OffsetDateTime accessTokenExpiry) {
    this.accessTokenExpiry = accessTokenExpiry;
  }

  public UserLoginPostDto isNewUser(Boolean isNewUser) {
    this.isNewUser = isNewUser;
    return this;
  }

  /**
   * Get isNewUser
   * @return isNewUser
  */
  @ApiModelProperty(value = "")


  public Boolean getIsNewUser() {
    return isNewUser;
  }

  public void setIsNewUser(Boolean isNewUser) {
    this.isNewUser = isNewUser;
  }

  public UserLoginPostDto userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  @ApiModelProperty(value = "")


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginPostDto userLoginPost = (UserLoginPostDto) o;
    return Objects.equals(this.accessToken, userLoginPost.accessToken) &&
        Objects.equals(this.accessTokenExpiry, userLoginPost.accessTokenExpiry) &&
        Objects.equals(this.isNewUser, userLoginPost.isNewUser) &&
        Objects.equals(this.userId, userLoginPost.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, accessTokenExpiry, isNewUser, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLoginPostDto {\n");
    
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    accessTokenExpiry: ").append(toIndentedString(accessTokenExpiry)).append("\n");
    sb.append("    isNewUser: ").append(toIndentedString(isNewUser)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

