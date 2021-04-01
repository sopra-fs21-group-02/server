/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ErrorResponseDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
@Validated
@Api(value = "message", description = "the message API")
public interface MessageApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /message : Add message to conversation
     * User can add message in a chart.
     *
     * @param chatMessageDto A message that needs to be created (required)
     * @return The message was succesfully added (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     */
    @ApiOperation(value = "Add message to conversation", nickname = "messagePost", notes = "User can add message in a chart.", tags={ "Conversations", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The message was succesfully added"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated") })
    @PostMapping(
        value = "/message",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> messagePost(@ApiParam(value = "A message that needs to be created" ,required=true )  @Valid @RequestBody ChatMessageDto chatMessageDto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}