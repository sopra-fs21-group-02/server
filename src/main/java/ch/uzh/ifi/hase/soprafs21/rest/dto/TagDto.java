package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TagDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TagDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  /**
   * Gets or Sets tagType
   */
  public enum TagTypeEnum {
    OFFERING("OFFERING"),
    
    LOOKINGFOR("LOOKINGFOR");

    private String value;

    TagTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TagTypeEnum fromValue(String value) {
      for (TagTypeEnum b : TagTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("tagType")
  private TagTypeEnum tagType;

  public TagDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public TagDto tagType(TagTypeEnum tagType) {
    this.tagType = tagType;
    return this;
  }

  /**
   * Get tagType
   * @return tagType
  */
  @ApiModelProperty(value = "")


  public TagTypeEnum getTagType() {
    return tagType;
  }

  public void setTagType(TagTypeEnum tagType) {
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
    return Objects.equals(this.id, tag.id) &&
        Objects.equals(this.name, tag.name) &&
        Objects.equals(this.tagType, tag.tagType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, tagType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

