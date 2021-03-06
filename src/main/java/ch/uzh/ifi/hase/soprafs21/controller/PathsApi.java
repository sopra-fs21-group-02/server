/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ErrorResponseDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ReviewDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
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
@Api(value = "paths", description = "the paths API")
public interface PathsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /paths : Create path
     * A path object that should be created
     *
     * @param walkingRouteDto  (required)
     * @return The path was succesfully created (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Create path", nickname = "addPath", notes = "A path object that should be created", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The path was succesfully created"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @PostMapping(
        value = "/paths",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> addPath(@ApiParam(value = "" ,required=true )  @Valid @RequestBody WalkingRouteDto walkingRouteDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /paths/{pathId} : Delete path with pathId
     * A user can delete a path created by him.
     *
     * @param pathId Numeric ID of the path (required)
     * @return A path with pathId was deleted (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Delete path with pathId", nickname = "deletePath", notes = "A user can delete a path created by him.", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "A path with pathId was deleted"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @DeleteMapping(
        value = "/paths/{pathId}",
        produces = { "application/json" }
    )
    default ResponseEntity<Void> deletePath(@ApiParam(value = "Numeric ID of the path",required=true) @PathVariable("pathId") Long pathId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /paths : Return all paths
     *
     * @param filter Filter to specify the visual area on the map (required)
     * @return A list of paths (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all paths", nickname = "getPaths", notes = "", response = WalkingRouteDto.class, responseContainer = "List", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of paths", response = WalkingRouteDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/paths",
        produces = { "application/json" }
    )
    default ResponseEntity<List<WalkingRouteDto>> getPaths(@NotNull @ApiParam(value = "Filter to specify the visual area on the map", required = true) @Valid AreaFilterDto filter) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"creator\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }, \"distance\" : 6.027456183070403, \"listOfCoordinates\" : [ { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 } ], \"description\" : \"description\", \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /paths/{pathId}/reviews : Return all review of walking path by pathId
     *
     * @param pathId Numeric ID of the path (required)
     * @return A list of reviews for a path with pathId (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all review of walking path by pathId", nickname = "pathsPathIdReviewsGet", notes = "", response = ReviewDto.class, responseContainer = "List", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of reviews for a path with pathId", response = ReviewDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/paths/{pathId}/reviews",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ReviewDto>> pathsPathIdReviewsGet(@ApiParam(value = "Numeric ID of the path",required=true) @PathVariable("pathId") Long pathId) throws Exception {
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
     * POST /paths/{pathId}/reviews : Add review to path with pathId
     * A user can add review to the walking path.
     *
     * @param pathId Numeric ID of the path (required)
     * @param reviewDto Review object that needs to be created (required)
     * @return The review was susscesfully added (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     */
    @ApiOperation(value = "Add review to path with pathId", nickname = "pathsPathIdReviewsPost", notes = "A user can add review to the walking path.", tags={ "Paths", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The review was susscesfully added"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated") })
    @PostMapping(
        value = "/paths/{pathId}/reviews",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> pathsPathIdReviewsPost(@ApiParam(value = "Numeric ID of the path",required=true) @PathVariable("pathId") Long pathId,@ApiParam(value = "Review object that needs to be created" ,required=true )  @Valid @RequestBody ReviewDto reviewDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
