package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserOverviewDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserOverviewDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private String name;

  @JsonProperty("profilePicture")
  private String profilePicture;

  @JsonProperty("status")
  private OnlineStatusDto status;

  @JsonProperty("latestLocation")
  private CoordinateDto latestLocation;

  public UserOverviewDto id(Long id) {
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

  public UserOverviewDto email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @ApiModelProperty(readOnly = true, value = "")

@javax.validation.constraints.Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserOverviewDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(readOnly = true, value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserOverviewDto profilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
    return this;
  }

  /**
   * Get profilePicture
   * @return profilePicture
  */
  @ApiModelProperty(readOnly = true, value = "")


  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public UserOverviewDto status(OnlineStatusDto status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")

  @Valid

  public OnlineStatusDto getStatus() {
    return status;
  }

  public void setStatus(OnlineStatusDto status) {
    this.status = status;
  }

  public UserOverviewDto latestLocation(CoordinateDto latestLocation) {
    this.latestLocation = latestLocation;
    return this;
  }

  /**
   * Get latestLocation
   * @return latestLocation
  */
  @ApiModelProperty(value = "")

  @Valid

  public CoordinateDto getLatestLocation() {
    return latestLocation;
  }

  public void setLatestLocation(CoordinateDto latestLocation) {
    this.latestLocation = latestLocation;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserOverviewDto userOverview = (UserOverviewDto) o;
    return Objects.equals(this.id, userOverview.id) &&
        Objects.equals(this.email, userOverview.email) &&
        Objects.equals(this.name, userOverview.name) &&
        Objects.equals(this.profilePicture, userOverview.profilePicture) &&
        Objects.equals(this.status, userOverview.status) &&
        Objects.equals(this.latestLocation, userOverview.latestLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, name, profilePicture, status, latestLocation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserOverviewDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    profilePicture: ").append(toIndentedString(profilePicture)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    latestLocation: ").append(toIndentedString(latestLocation)).append("\n");
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

