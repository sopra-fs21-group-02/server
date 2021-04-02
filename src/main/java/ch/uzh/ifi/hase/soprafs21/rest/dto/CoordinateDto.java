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
 * CoordinateDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CoordinateDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("latitude")
  private Double latitude;

  public CoordinateDto longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * @return longitude
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public CoordinateDto latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * @return latitude
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoordinateDto coordinate = (CoordinateDto) o;
    return Objects.equals(this.longitude, coordinate.longitude) &&
        Objects.equals(this.latitude, coordinate.latitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(longitude, latitude);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoordinateDto {\n");
    
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
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

