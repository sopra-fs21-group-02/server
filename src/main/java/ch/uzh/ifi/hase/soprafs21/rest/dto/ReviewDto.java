package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReviewDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
public class ReviewDto   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("creator")
  private UserDto creator;

  @JsonProperty("rating")
  private Integer rating;

  @JsonProperty("text")
  private String text;

  @JsonProperty("timeStamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timeStamp;

  public ReviewDto id(Long id) {
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

  public ReviewDto creator(UserDto creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserDto getCreator() {
    return creator;
  }

  public void setCreator(UserDto creator) {
    this.creator = creator;
  }

  public ReviewDto rating(Integer rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * minimum: 1
   * maximum: 5
   * @return rating
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Min(1) @Max(5) 
  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public ReviewDto text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
  */
  @ApiModelProperty(value = "")


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ReviewDto timeStamp(OffsetDateTime timeStamp) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewDto review = (ReviewDto) o;
    return Objects.equals(this.id, review.id) &&
        Objects.equals(this.creator, review.creator) &&
        Objects.equals(this.rating, review.rating) &&
        Objects.equals(this.text, review.text) &&
        Objects.equals(this.timeStamp, review.timeStamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creator, rating, text, timeStamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
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

