package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ReviewDto;
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
 * ParkDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
public class ParkDto   {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("creator")
  private UserDto creator;

  @JsonProperty("coordinate")
  private CoordinateDto coordinate;

  @JsonProperty("reviews")
  @Valid
  private List<ReviewDto> reviews = null;

  public ParkDto id(Long id) {
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

  public ParkDto creator(UserDto creator) {
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

  public ParkDto coordinate(CoordinateDto coordinate) {
    this.coordinate = coordinate;
    return this;
  }

  /**
   * Get coordinate
   * @return coordinate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CoordinateDto getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(CoordinateDto coordinate) {
    this.coordinate = coordinate;
  }

  public ParkDto reviews(List<ReviewDto> reviews) {
    this.reviews = reviews;
    return this;
  }

  public ParkDto addReviewsItem(ReviewDto reviewsItem) {
    if (this.reviews == null) {
      this.reviews = new ArrayList<>();
    }
    this.reviews.add(reviewsItem);
    return this;
  }

  /**
   * Get reviews
   * @return reviews
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ReviewDto> getReviews() {
    return reviews;
  }

  public void setReviews(List<ReviewDto> reviews) {
    this.reviews = reviews;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParkDto park = (ParkDto) o;
    return Objects.equals(this.id, park.id) &&
        Objects.equals(this.creator, park.creator) &&
        Objects.equals(this.coordinate, park.coordinate) &&
        Objects.equals(this.reviews, park.reviews);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creator, coordinate, reviews);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParkDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    coordinate: ").append(toIndentedString(coordinate)).append("\n");
    sb.append("    reviews: ").append(toIndentedString(reviews)).append("\n");
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

