/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ErrorResponseDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ParkDto;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
@Api(value = "parks", description = "the parks API")
public interface ParksApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /parks : Create park
     * A park object that should be created
     *
     * @param parkDto  (required)
     * @return The park was succesfully created (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Create park", nickname = "addPark", notes = "A park object that should be created", tags={ "Parks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The park was succesfully created"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @PostMapping(
        value = "/parks",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> addPark(@ApiParam(value = "" ,required=true )  @Valid @RequestBody ParkDto parkDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /parks/{parkId} : Delete park with parkId
     * A user can delete a park created by him.
     *
     * @param parkId Numeric ID of the park (required)
     * @return A park with parkId was deleted (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Delete park with parkId", nickname = "deletePark", notes = "A user can delete a park created by him.", tags={ "Parks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "A park with parkId was deleted"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @DeleteMapping(
        value = "/parks/{parkId}",
        produces = { "application/json" }
    )
    default ResponseEntity<Void> deletePark(@ApiParam(value = "Numeric ID of the park",required=true) @PathVariable("parkId") Long parkId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /parks : Return all parks
     *
     * @param filter Filter to specify the visual area on the map (required)
     * @return A list of parks (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all parks", nickname = "getParks", notes = "", response = ParkDto.class, responseContainer = "List", tags={ "Parks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of parks", response = ParkDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/parks",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ParkDto>> getParks(@NotNull @ApiParam(value = "Filter to specify the visual area on the map", required = true) @Valid AreaFilterDto filter) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"coordinate\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"creatorId\" : 6, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /parks/{parkId}/reviews : Return all review of park by parkId
     *
     * @param parkId Numeric ID of the park (required)
     * @return A list of reviews for a park with parkId (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all review of park by parkId", nickname = "parksParkIdReviewsGet", notes = "", response = ReviewDto.class, responseContainer = "List", tags={ "Parks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of reviews for a park with parkId", response = ReviewDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/parks/{parkId}/reviews",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ReviewDto>> parksParkIdReviewsGet(@ApiParam(value = "Numeric ID of the park",required=true) @PathVariable("parkId") Long parkId) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"creator\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"dogs\" : [ { \"name\" : \"name\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 1, \"breed\" : \"breed\" }, { \"name\" : \"name\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 1, \"breed\" : \"breed\" } ], \"bio\" : \"bio\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 0, \"email\" : \"email\", \"tags\" : [ { \"name\" : \"name\", \"tagType\" : \"OFFERING\", \"id\" : 6 }, { \"name\" : \"name\", \"tagType\" : \"OFFERING\", \"id\" : 6 } ] }, \"rating\" : 3, \"id\" : 0, \"text\" : \"text\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /parks/{parkId}/reviews : Add review to park with parkId
     * A user can add review to the park.
     *
     * @param parkId Numeric ID of the park (required)
     * @param reviewDto Review object that needs to be created (required)
     * @return The review was susscesfully added (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     */
    @ApiOperation(value = "Add review to park with parkId", nickname = "parksParkIdReviewsPost", notes = "A user can add review to the park.", tags={ "Parks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The review was susscesfully added"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated") })
    @PostMapping(
        value = "/parks/{parkId}/reviews",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> parksParkIdReviewsPost(@ApiParam(value = "Numeric ID of the park",required=true) @PathVariable("parkId") Long parkId,@ApiParam(value = "Review object that needs to be created" ,required=true )  @Valid @RequestBody ReviewDto reviewDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
