/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.ErrorResponseDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ReviewDto;
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
@Api(value = "path", description = "the path API")
public interface PathApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /path/{pathId}/reviews : Return all review of walking path by pathId
     *
     * @param pathId Numeric ID of the path (required)
     * @return A list of reviews for a path with pathId (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all review of walking path by pathId", nickname = "pathPathIdReviewsGet", notes = "", response = ReviewDto.class, responseContainer = "List", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of reviews for a path with pathId", response = ReviewDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/path/{pathId}/reviews",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ReviewDto>> pathPathIdReviewsGet(@ApiParam(value = "Numeric ID of the path",required=true) @PathVariable("pathId") Long pathId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"creator\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"dogs\" : [ { \"profilePicture\" : \"profilePicture\", \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 7.061401241503109, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 2, \"breed\" : \"breed\" }, { \"profilePicture\" : \"profilePicture\", \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 7.061401241503109, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 2, \"breed\" : \"breed\" } ], \"bio\" : \"bio\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 0, \"conversations\" : [ { \"id\" : 5, \"message\" : [ { \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"unread\" : true, \"id\" : 5, \"message\" : \"message\" }, { \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"unread\" : true, \"id\" : 5, \"message\" : \"message\" } ] }, { \"id\" : 5, \"message\" : [ { \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"unread\" : true, \"id\" : 5, \"message\" : \"message\" }, { \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"unread\" : true, \"id\" : 5, \"message\" : \"message\" } ] } ], \"email\" : \"email\", \"tags\" : [ { \"name\" : \"name\", \"tagType\" : \"OFFERING\" }, { \"name\" : \"name\", \"tagType\" : \"OFFERING\" } ] }, \"rating\" : 1, \"id\" : 6, \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /path/{pathId}/reviews : Add review to path with pathId
     * A user can add review to the walking path.
     *
     * @param pathId Numeric ID of the path (required)
     * @param reviewDto Review object that needs to be created (required)
     * @return The review was susscesfully added (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     */
    @ApiOperation(value = "Add review to path with pathId", nickname = "pathPathIdReviewsPost", notes = "A user can add review to the walking path.", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The review was susscesfully added"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated") })
    @PostMapping(
        value = "/path/{pathId}/reviews",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> pathPathIdReviewsPost(@ApiParam(value = "Numeric ID of the path",required=true) @PathVariable("pathId") Long pathId,@ApiParam(value = "Review object that needs to be created" ,required=true )  @Valid @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}