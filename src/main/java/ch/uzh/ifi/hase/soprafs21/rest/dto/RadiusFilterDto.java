package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RadiusFilterDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class RadiusFilterDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("radius")
  private Integer radius;

  @JsonProperty("coordinate")
  private CoordinateDto coordinate;

  public RadiusFilterDto radius(Integer radius) {
    this.radius = radius;
    return this;
  }

  /**
   * Get radius
   * @return radius
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getRadius() {
    return radius;
  }

  public void setRadius(Integer radius) {
    this.radius = radius;
  }

  public RadiusFilterDto coordinate(CoordinateDto coordinate) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RadiusFilterDto radiusFilter = (RadiusFilterDto) o;
    return Objects.equals(this.radius, radiusFilter.radius) &&
        Objects.equals(this.coordinate, radiusFilter.coordinate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(radius, coordinate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RadiusFilterDto {\n");
    
    sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
    sb.append("    coordinate: ").append(toIndentedString(coordinate)).append("\n");
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

