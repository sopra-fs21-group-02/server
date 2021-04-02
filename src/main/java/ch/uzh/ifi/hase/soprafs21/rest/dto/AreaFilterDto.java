package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
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
 * AreaFilterDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AreaFilterDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("visibleArea")
  @Valid
  private List<CoordinateDto> visibleArea = new ArrayList<>();

  public AreaFilterDto visibleArea(List<CoordinateDto> visibleArea) {
    this.visibleArea = visibleArea;
    return this;
  }

  public AreaFilterDto addVisibleAreaItem(CoordinateDto visibleAreaItem) {
    this.visibleArea.add(visibleAreaItem);
    return this;
  }

  /**
   * Get visibleArea
   * @return visibleArea
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
@Size(min=3) 
  public List<CoordinateDto> getVisibleArea() {
    return visibleArea;
  }

  public void setVisibleArea(List<CoordinateDto> visibleArea) {
    this.visibleArea = visibleArea;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AreaFilterDto areaFilter = (AreaFilterDto) o;
    return Objects.equals(this.visibleArea, areaFilter.visibleArea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(visibleArea);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AreaFilterDto {\n");
    
    sb.append("    visibleArea: ").append(toIndentedString(visibleArea)).append("\n");
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

