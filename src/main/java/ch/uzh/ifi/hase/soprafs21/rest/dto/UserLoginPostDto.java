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
 * UserLoginPostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserLoginPostDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("jwtToken")
  private String jwtToken;

  @JsonProperty("isNewUser")
  private Boolean isNewUser;

  public UserLoginPostDto jwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
    return this;
  }

  /**
   * Get jwtToken
   * @return jwtToken
  */
  @ApiModelProperty(value = "")


  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginPostDto userLoginPost = (UserLoginPostDto) o;
    return Objects.equals(this.jwtToken, userLoginPost.jwtToken) &&
        Objects.equals(this.isNewUser, userLoginPost.isNewUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwtToken, isNewUser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLoginPostDto {\n");
    
    sb.append("    jwtToken: ").append(toIndentedString(jwtToken)).append("\n");
    sb.append("    isNewUser: ").append(toIndentedString(isNewUser)).append("\n");
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

