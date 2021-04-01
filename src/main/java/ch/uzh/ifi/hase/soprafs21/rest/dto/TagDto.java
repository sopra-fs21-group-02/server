package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;

import ch.uzh.ifi.hase.soprafs21.constant.TagType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TagDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
public class TagDto   {
  @JsonProperty("name")
  private String name;

    @JsonProperty("tagType")
  private TagType tagType;

  public TagDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TagDto tagType(TagType tagType) {
    this.tagType = tagType;
    return this;
  }

  /**
   * Get tagType
   * @return tagType
  */
  @ApiModelProperty(value = "")


  public TagType getTagType() {
    return tagType;
  }

  public void setTagType(TagType tagType) {
    this.tagType = tagType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagDto tag = (TagDto) o;
    return Objects.equals(this.name, tag.name) &&
        Objects.equals(this.tagType, tag.tagType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, tagType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    tagType: ").append(toIndentedString(tagType)).append("\n");
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

