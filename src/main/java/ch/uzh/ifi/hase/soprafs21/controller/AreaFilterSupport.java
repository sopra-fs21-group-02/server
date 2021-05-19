package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class AreaFilterSupport extends PropertyEditorSupport {

    private ObjectMapper objectMapper;

    public AreaFilterSupport(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            AreaFilterDto filterDto;
            try {
                filterDto = objectMapper.readValue(text, AreaFilterDto.class);
            } catch (JsonProcessingException  e) {
                throw new IllegalArgumentException(e);
            }
            setValue(filterDto);
        }
    }

}
