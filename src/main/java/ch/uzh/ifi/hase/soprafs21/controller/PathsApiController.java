package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.PathDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.PathService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class PathsApiController implements PathsApi {

    private final NativeWebRequest request;

    private final UserService userService;
    private final PathService pathService;


    @org.springframework.beans.factory.annotation.Autowired
    public PathsApiController(NativeWebRequest request, UserService userService, PathService pathService) {
        this.request = request;
        this.userService = userService;
        this.pathService = pathService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> addPath(@Valid WalkingRouteDto walkingRouteDto) throws Exception {
        if (walkingRouteDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not allowed in POST");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(walkingRouteDto.getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to add this Route");
        }

        Path path = PathDTOMapper.INSTANCE.toPathEntity(walkingRouteDto);
        path.setCreator(userService.getUserById(walkingRouteDto.getCreator().getId()));
        pathService.savePath(path);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Override
    public ResponseEntity<Void> deletePath(Long userId, Long pathId) throws Exception {
        if (!userService.isRequesterAndAuthenticatedUserTheSame(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to delete this Route");
        }
        pathService.deletePath(userId,pathId);

        return ResponseEntity.noContent().build();
    }
}
