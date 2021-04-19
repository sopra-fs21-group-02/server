package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.GenderDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.TagDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private String name;

  @JsonProperty("gender")
  private GenderDto gender;

  @JsonProperty("dateOfBirth")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  @JsonProperty("bio")
  private String bio;

  @JsonProperty("status")
  private OnlineStatusDto status;

  @JsonProperty("profilePicture")
  private String profilePicture;

  @JsonProperty("tags")
  @Valid
  private List<TagDto> tags = null;

  @JsonProperty("dogs")
  @Valid
  private List<DogDto> dogs = null;

  @JsonProperty("latestLocation")
  private CoordinateDto latestLocation;

  public UserDto id(Long id) {
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

  public UserDto email(String email) {
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

  public UserDto name(String name) {
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

  public UserDto gender(GenderDto gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
  */
  @ApiModelProperty(value = "")

  @Valid

  public GenderDto getGender() {
    return gender;
  }

  public void setGender(GenderDto gender) {
    this.gender = gender;
  }

  public UserDto dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
   * @return dateOfBirth
  */
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public UserDto bio(String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * Get bio
   * @return bio
  */
  @ApiModelProperty(value = "")


  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public UserDto status(OnlineStatusDto status) {
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

  public UserDto profilePicture(String profilePicture) {
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

  public UserDto tags(List<TagDto> tags) {
    this.tags = tags;
    return this;
  }

  public UserDto addTagsItem(TagDto tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<TagDto> getTags() {
    return tags;
  }

  public void setTags(List<TagDto> tags) {
    this.tags = tags;
  }

  public UserDto dogs(List<DogDto> dogs) {
    this.dogs = dogs;
    return this;
  }

  public UserDto addDogsItem(DogDto dogsItem) {
    if (this.dogs == null) {
      this.dogs = new ArrayList<>();
    }
    this.dogs.add(dogsItem);
    return this;
  }

  /**
   * Get dogs
   * @return dogs
  */
  @ApiModelProperty(readOnly = true, value = "")

  @Valid

  public List<DogDto> getDogs() {
    return dogs;
  }

  public void setDogs(List<DogDto> dogs) {
    this.dogs = dogs;
  }

  public UserDto latestLocation(CoordinateDto latestLocation) {
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
    UserDto user = (UserDto) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.name, user.name) &&
        Objects.equals(this.gender, user.gender) &&
        Objects.equals(this.dateOfBirth, user.dateOfBirth) &&
        Objects.equals(this.bio, user.bio) &&
        Objects.equals(this.status, user.status) &&
        Objects.equals(this.profilePicture, user.profilePicture) &&
        Objects.equals(this.tags, user.tags) &&
        Objects.equals(this.dogs, user.dogs) &&
        Objects.equals(this.latestLocation, user.latestLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, name, gender, dateOfBirth, bio, status, profilePicture, tags, dogs, latestLocation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    bio: ").append(toIndentedString(bio)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    profilePicture: ").append(toIndentedString(profilePicture)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    dogs: ").append(toIndentedString(dogs)).append("\n");
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

