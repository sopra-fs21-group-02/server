package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ErrorResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ErrorResponseDto  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("reason")
  private String reason;

  @JsonProperty("statusCode")
  private BigDecimal statusCode;

  public ErrorResponseDto reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
  */
  @ApiModelProperty(value = "")


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public ErrorResponseDto statusCode(BigDecimal statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Get statusCode
   * @return statusCode
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(BigDecimal statusCode) {
    this.statusCode = statusCode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponseDto errorResponse = (ErrorResponseDto) o;
    return Objects.equals(this.reason, errorResponse.reason) &&
        Objects.equals(this.statusCode, errorResponse.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reason, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponseDto {\n");
    
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
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

