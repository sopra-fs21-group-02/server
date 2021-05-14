package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
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
  private UserOverviewDto creator;

  @JsonProperty("listOfCoordinates")
  @Valid
  private List<CoordinateDto> listOfCoordinates = new ArrayList<>();

  @JsonProperty("distance")
  private Double distance;

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

  public WalkingRouteDto creator(UserOverviewDto creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserOverviewDto getCreator() {
    return creator;
  }

  public void setCreator(UserOverviewDto creator) {
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

  public WalkingRouteDto distance(Double distance) {
    this.distance = distance;
    return this;
  }

  /**
   * Get distance
   * @return distance
  */
  @ApiModelProperty(value = "")


  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
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
        Objects.equals(this.distance, walkingRoute.distance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creator, listOfCoordinates, distance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WalkingRouteDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    listOfCoordinates: ").append(toIndentedString(listOfCoordinates)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
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

