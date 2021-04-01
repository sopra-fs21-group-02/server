package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;

import ch.uzh.ifi.hase.soprafs21.constant.Gender;
import ch.uzh.ifi.hase.soprafs21.constant.OnlineStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
public class UserDto   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private String name;

  @JsonProperty("gender")
  private Gender gender;

  @JsonProperty("dateOfBirth")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  @JsonProperty("bio")
  private String bio;

  @JsonProperty("status")
  private OnlineStatus status;

  @JsonProperty("profilePicture")
  private String profilePicture;

  @JsonProperty("latestLocation")
  private CoordinateDto latestLocation;

  @JsonProperty("tags")
  @Valid
  private List<TagDto> tags = null;

  @JsonProperty("conversations")
  @Valid
  private List<ConversationDto> conversations = null;

  @JsonProperty("dogs")
  @Valid
  private List<DogDto> dogs = null;

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

  public UserDto gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
  */
  @ApiModelProperty(value = "")

  @Valid

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
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

  public UserDto status(OnlineStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")

  @Valid

  public OnlineStatus getStatus() {
    return status;
  }

  public void setStatus(OnlineStatus status) {
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

  public UserDto conversations(List<ConversationDto> conversations) {
    this.conversations = conversations;
    return this;
  }

  public UserDto addConversationsItem(ConversationDto conversationsItem) {
    if (this.conversations == null) {
      this.conversations = new ArrayList<>();
    }
    this.conversations.add(conversationsItem);
    return this;
  }

  /**
   * Get conversations
   * @return conversations
  */
  @ApiModelProperty(readOnly = true, value = "")

  @Valid

  public List<ConversationDto> getConversations() {
    return conversations;
  }

  public void setConversations(List<ConversationDto> conversations) {
    this.conversations = conversations;
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
        Objects.equals(this.latestLocation, user.latestLocation) &&
        Objects.equals(this.tags, user.tags) &&
        Objects.equals(this.conversations, user.conversations) &&
        Objects.equals(this.dogs, user.dogs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, name, gender, dateOfBirth, bio, status, profilePicture, latestLocation, tags, conversations, dogs);
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
    sb.append("    latestLocation: ").append(toIndentedString(latestLocation)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    conversations: ").append(toIndentedString(conversations)).append("\n");
    sb.append("    dogs: ").append(toIndentedString(dogs)).append("\n");
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
