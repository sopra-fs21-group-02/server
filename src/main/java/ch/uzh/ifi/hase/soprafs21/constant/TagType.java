package ch.uzh.ifi.hase.soprafs21.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TagType {

    OFFERING("OFFERING"),

    LOOKINGFOR("LOOKINGFOR");

    private String value;

    TagType(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return value; }

    @Override
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static TagType fromValue(String value) {
        for (TagType b : TagType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }



}

