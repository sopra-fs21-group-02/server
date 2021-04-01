package ch.uzh.ifi.hase.soprafs21.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets OnlineStatus
 */
public enum OnlineStatus {
  
  ONLINE("ONLINE"),
  
  OFFLINE("OFFLINE");

  private String value;

  OnlineStatus(String value) {
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
  public static OnlineStatus fromValue(String value) {
    for (OnlineStatus b : OnlineStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

