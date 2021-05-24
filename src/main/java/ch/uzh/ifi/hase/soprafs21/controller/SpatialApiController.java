package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class SpatialApiController {
    protected final ObjectMapper objectMapper;

    protected SpatialApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AreaFilterDto.class, new AreaFilterSupport(objectMapper));
    }
}
