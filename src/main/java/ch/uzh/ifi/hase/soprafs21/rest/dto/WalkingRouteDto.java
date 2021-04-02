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
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WalkingRouteDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class WalkingRouteDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("creator")
  private UserDto creator;

  @JsonProperty("listOfCoordinates")
  @Valid
  private List<CoordinateDto> listOfCoordinates = new ArrayList<>();

  @JsonProperty("distance")
  private Integer distance;

  @JsonProperty("reviews")
  @Valid
  private List<ReviewDto> reviews = null;

  public WalkingRouteDto id(Long id) {
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

  public WalkingRouteDto creator(UserDto creator) {
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

  public WalkingRouteDto listOfCoordinates(List<CoordinateDto> listOfCoordinates) {
    this.listOfCoordinates = listOfCoordinates;
    return this;
  }

  public WalkingRouteDto addListOfCoordinatesItem(CoordinateDto listOfCoordinatesItem) {
    this.listOfCoordinates.add(listOfCoordinatesItem);
    return this;
  }

  /**
   * Get listOfCoordinates
   * @return listOfCoordinates
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<CoordinateDto> getListOfCoordinates() {
    return listOfCoordinates;
  }

  public void setListOfCoordinates(List<CoordinateDto> listOfCoordinates) {
    this.listOfCoordinates = listOfCoordinates;
  }

  public WalkingRouteDto distance(Integer distance) {
    this.distance = distance;
    return this;
  }

  /**
   * Get distance
   * @return distance
  */
  @ApiModelProperty(value = "")


  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public WalkingRouteDto reviews(List<ReviewDto> reviews) {
    this.reviews = reviews;
    return this;
  }

  public WalkingRouteDto addReviewsItem(ReviewDto reviewsItem) {
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
    WalkingRouteDto walkingRoute = (WalkingRouteDto) o;
    return Objects.equals(this.id, walkingRoute.id) &&
        Objects.equals(this.creator, walkingRoute.creator) &&
        Objects.equals(this.listOfCoordinates, walkingRoute.listOfCoordinates) &&
        Objects.equals(this.distance, walkingRoute.distance) &&
        Objects.equals(this.reviews, walkingRoute.reviews);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creator, listOfCoordinates, distance, reviews);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WalkingRouteDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    listOfCoordinates: ").append(toIndentedString(listOfCoordinates)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
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

