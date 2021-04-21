/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ChatMessageDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ConversationDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.CoordinateDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.DogDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ErrorResponseDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.RadiusFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserLoginPostDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
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
@Api(value = "users", description = "the users API")
public interface UsersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /users/{userId}/conversations : Return all conversations of user by userId
     *
     * @param userId Numeric ID of the user (required)
     * @return A list of conversations for user with userId (status code 200)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all conversations of user by userId", nickname = "getAllConversations", notes = "", response = ConversationDto.class, responseContainer = "List", tags={ "Conversations", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of conversations for user with userId", response = ConversationDto.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId}/conversations",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ConversationDto>> getAllConversations(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"lastMessage\" : { \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }, \"sender\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }, \"unread\" : true, \"id\" : 0, \"message\" : \"message\" }, \"participant\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId1}/conversations/{userId2} : Return all messages between user1 and user2
     *
     * @param userId1 Numeric ID of the user1 (required)
     * @param userId2 Numeric ID of the user2 (required)
     * @return A list of messages with user with userId2 (status code 200)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all messages between user1 and user2", nickname = "getAllMessages", notes = "", response = ChatMessageDto.class, responseContainer = "List", tags={ "Conversations", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of messages with user with userId2", response = ChatMessageDto.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId1}/conversations/{userId2}",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ChatMessageDto>> getAllMessages(@ApiParam(value = "Numeric ID of the user1",required=true) @PathVariable("userId1") Long userId1,@ApiParam(value = "Numeric ID of the user2",required=true) @PathVariable("userId2") Long userId2) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"timeStamp\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }, \"sender\" : { \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }, \"unread\" : true, \"id\" : 0, \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users : Return all users
     *
     * @param areaFilter  (optional)
     * @param radiusFilter  (optional)
     * @return A list of Users (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all users", nickname = "getUsers", notes = "", response = UserOverviewDto.class, responseContainer = "List", tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of Users", response = UserOverviewDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users",
        produces = { "application/json" }
    )
    default ResponseEntity<List<UserOverviewDto>> getUsers(@ApiParam(value = "") @Valid AreaFilterDto areaFilter,@ApiParam(value = "") @Valid RadiusFilterDto radiusFilter) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"id\" : 0, \"email\" : \"email\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /users/{userId}/location : Update user&#39;s location
     * The location of the user is updated.
     *
     * @param userId Numeric ID of the user to update (required)
     * @param coordinateDto Coordinate object that needs to be updated in user with userId (required)
     * @return The user&#39;s location was susscesfully updated (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Update user's location", nickname = "updateLocation", notes = "The location of the user is updated.", tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The user's location was susscesfully updated"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PutMapping(
        value = "/users/{userId}/location",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateLocation(@ApiParam(value = "Numeric ID of the user to update",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Coordinate object that needs to be updated in user with userId" ,required=true )  @Valid @RequestBody CoordinateDto coordinateDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /users/login : User is logged in/registered
     * A user can register/login.
     *
     * @param userLoginDto User object that needs to be created (required)
     * @return User is logged-in (status code 200)
     *         or Invalid Request (status code 400)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "User is logged in/registered", nickname = "usersLoginPost", notes = "A user can register/login.", response = UserLoginPostDto.class, tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User is logged-in", response = UserLoginPostDto.class),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PostMapping(
        value = "/users/login",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<UserLoginPostDto> usersLoginPost(@ApiParam(value = "User object that needs to be created" ,required=true )  @Valid @RequestBody UserLoginDto userLoginDto) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"accessTokenExpiry\" : \"2000-01-23T04:56:07.000+00:00\", \"accessToken\" : \"accessToken\", \"isNewUser\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /users/refreshToken : Silent refresh of tokens
     * Update refresh token.
     *
     * @param refreshToken  (required)
     * @return Refresh token and access token regenerated (status code 200)
     *         or Invalid Request (status code 400)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Silent refresh of tokens", nickname = "usersRefreshTokenPut", notes = "Update refresh token.", response = UserLoginPostDto.class, tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Refresh token and access token regenerated", response = UserLoginPostDto.class),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PutMapping(
        value = "/users/refreshToken",
        produces = { "application/json" }
    )
    default ResponseEntity<UserLoginPostDto> usersRefreshTokenPut(@NotNull @ApiParam(value = "",required=true) @CookieValue("refresh_token") String refreshToken) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"accessTokenExpiry\" : \"2000-01-23T04:56:07.000+00:00\", \"accessToken\" : \"accessToken\", \"isNewUser\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /users/{userId} : Delete user with userId
     * A user can delete his own profile.
     *
     * @param userId Numeric ID of the user to delete (required)
     * @return A user with id was deleted (status code 204)
     *         or Invalid Request (status code 400)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Delete user with userId", nickname = "usersUserIdDelete", notes = "A user can delete his own profile.", tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "A user with id was deleted"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @DeleteMapping(
        value = "/users/{userId}",
        produces = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdDelete(@ApiParam(value = "Numeric ID of the user to delete",required=true) @PathVariable("userId") Long userId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /users/{userId}/dogs/{dogId} : Delete dog with dogId
     * A user can delete his own dog.
     *
     * @param userId Numeric ID of the user (required)
     * @param dogId Numeric ID of the dog to delete (required)
     * @return A dog with dogId was deleted (status code 204)
     *         or Invalid Request (status code 400)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Delete dog with dogId", nickname = "usersUserIdDogsDogIdDelete", notes = "A user can delete his own dog.", tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "A dog with dogId was deleted"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @DeleteMapping(
        value = "/users/{userId}/dogs/{dogId}",
        produces = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdDogsDogIdDelete(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Numeric ID of the dog to delete",required=true) @PathVariable("dogId") Long dogId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId}/dogs/{dogId} : Return dog of user by dogId
     *
     * @param userId Numeric ID of the user (required)
     * @param dogId Numeric ID of the dog (required)
     * @return A Dog object with dogId (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return dog of user by dogId", nickname = "usersUserIdDogsDogIdGet", notes = "", response = DogDto.class, tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A Dog object with dogId", response = DogDto.class),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId}/dogs/{dogId}",
        produces = { "application/json" }
    )
    default ResponseEntity<DogDto> usersUserIdDogsDogIdGet(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Numeric ID of the dog",required=true) @PathVariable("dogId") Long dogId) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 1.4658129805029452, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 6, \"breed\" : \"breed\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId}/dogs/{dogId}/image
     * Get dog&#39;s profile image
     *
     * @param userId Numeric ID of the user (required)
     * @param dogId Numeric ID of the dog (required)
     * @return Returns dog&#39;s profile image (status code 200)
     *         or Invalid Request (status code 400)
     *         or User not permitted (status code 403)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "", nickname = "usersUserIdDogsDogIdImageGet", notes = "Get dog's profile image", response = org.springframework.core.io.Resource.class, tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns dog's profile image", response = org.springframework.core.io.Resource.class),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId}/dogs/{dogId}/image",
        produces = { "image/gif", "image/jpeg", "image/png", "image/tiff", "application/json" }
    )
    default ResponseEntity<org.springframework.core.io.Resource> usersUserIdDogsDogIdImageGet(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Numeric ID of the dog",required=true) @PathVariable("dogId") Long dogId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /users/{userId}/dogs/{dogId}/image
     * Upload dog&#39;s profile image
     *
     * @param userId Numeric ID of the user (required)
     * @param dogId Numeric ID of the dog (required)
     * @param body  (required)
     * @return The image has been successfully uploaded (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "", nickname = "usersUserIdDogsDogIdImagePost", notes = "Upload dog's profile image", tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The image has been successfully uploaded"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PostMapping(
        value = "/users/{userId}/dogs/{dogId}/image",
        produces = { "application/json" },
        consumes = { "image/gif", "image/jpeg", "image/png", "image/tiff" }
    )
    default ResponseEntity<Void> usersUserIdDogsDogIdImagePost(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Numeric ID of the dog",required=true) @PathVariable("dogId") Long dogId,@ApiParam(value = "" ,required=true )  @Valid @RequestBody org.springframework.core.io.Resource body) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /users/{userId}/dogs/{dogId} : Update an existing dog
     * A user can edit dog belonging to his profile.
     *
     * @param userId Numeric ID of the user (required)
     * @param dogId Numeric ID of the dog to update (required)
     * @param dogDto Dog object that needs to be updated (required)
     * @return The dog&#39;s details were susscesfully updated (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Update an existing dog", nickname = "usersUserIdDogsDogIdPut", notes = "A user can edit dog belonging to his profile.", tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The dog's details were susscesfully updated"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PutMapping(
        value = "/users/{userId}/dogs/{dogId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdDogsDogIdPut(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Numeric ID of the dog to update",required=true) @PathVariable("dogId") Long dogId,@ApiParam(value = "Dog object that needs to be updated" ,required=true )  @Valid @RequestBody DogDto dogDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId}/dogs : Return all dogs of user with userId
     *
     * @param userId Numeric ID of the user (required)
     * @return A list of Dogs (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return all dogs of user with userId", nickname = "usersUserIdDogsGet", notes = "", response = DogDto.class, responseContainer = "List", tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of Dogs", response = DogDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId}/dogs",
        produces = { "application/json" }
    )
    default ResponseEntity<List<DogDto>> usersUserIdDogsGet(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 1.4658129805029452, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 6, \"breed\" : \"breed\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /users/{userId}/dogs : Add dog to user profile
     * A user can add dog to his own profile.
     *
     * @param userId Numeric ID of the user (required)
     * @param dogDto Dog object that needs to be created (required)
     * @return The dog was susscesfully created (status code 201)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Add dog to user profile", nickname = "usersUserIdDogsPost", notes = "A user can add dog to his own profile.", tags={ "Dogs", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "The dog was susscesfully created"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @PostMapping(
        value = "/users/{userId}/dogs",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdDogsPost(@ApiParam(value = "Numeric ID of the user",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "Dog object that needs to be created" ,required=true )  @Valid @RequestBody DogDto dogDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /users/{userId} : Return user by userId
     *
     * @param userId Numeric ID of the user to get (required)
     * @return A user with id (status code 200)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Return user by userId", nickname = "usersUserIdGet", notes = "", response = UserDto.class, tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A user with id", response = UserDto.class),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @GetMapping(
        value = "/users/{userId}",
        produces = { "application/json" }
    )
    default ResponseEntity<UserDto> usersUserIdGet(@ApiParam(value = "Numeric ID of the user to get",required=true) @PathVariable("userId") Long userId) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"profilePicture\" : \"profilePicture\", \"latestLocation\" : { \"latitude\" : 1.4658129805029452, \"longitude\" : 6.027456183070403 }, \"name\" : \"name\", \"dogs\" : [ { \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 1.4658129805029452, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 6, \"breed\" : \"breed\" }, { \"color\" : \"color\", \"name\" : \"name\", \"weight\" : 1.4658129805029452, \"description\" : \"description\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 6, \"breed\" : \"breed\" } ], \"bio\" : \"bio\", \"dateOfBirth\" : \"2000-01-23\", \"id\" : 0, \"email\" : \"email\", \"tags\" : [ { \"name\" : \"name\", \"tagType\" : \"OFFERING\" }, { \"name\" : \"name\", \"tagType\" : \"OFFERING\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /users/{userId}/logout : Log out from account
     * A user can log out from his/her account.
     *
     * @param userId Numeric ID of the user to logout (required)
     * @return The user logged out (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     */
    @ApiOperation(value = "Log out from account", nickname = "usersUserIdLogoutPut", notes = "A user can log out from his/her account.", tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The user logged out"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted") })
    @PutMapping(
        value = "/users/{userId}/logout",
        produces = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdLogoutPut(@ApiParam(value = "Numeric ID of the user to logout",required=true) @PathVariable("userId") Long userId) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /users/{userId} : Update an existing user
     * A user can update only his own profile.
     *
     * @param userId Numeric ID of the user to update (required)
     * @param userDto User object that needs to be updated (required)
     * @return The user was susscesfully updated (status code 204)
     *         or Invalid Request (status code 400)
     *         or User unauthenticated (status code 401)
     *         or User not permitted (status code 403)
     *         or Resource not found (status code 404)
     */
    @ApiOperation(value = "Update an existing user", nickname = "usersUserIdPut", notes = "A user can update only his own profile.", tags={ "Users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The user was susscesfully updated"),
        @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDto.class),
        @ApiResponse(code = 401, message = "User unauthenticated"),
        @ApiResponse(code = 403, message = "User not permitted"),
        @ApiResponse(code = 404, message = "Resource not found") })
    @PutMapping(
        value = "/users/{userId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> usersUserIdPut(@ApiParam(value = "Numeric ID of the user to update",required=true) @PathVariable("userId") Long userId,@ApiParam(value = "User object that needs to be updated" ,required=true )  @Valid @RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
