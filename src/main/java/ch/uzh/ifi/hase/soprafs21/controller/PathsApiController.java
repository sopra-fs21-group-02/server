package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.WalkingRouteDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.PathDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.PathService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class PathsApiController extends SpatialApiController implements PathsApi {

    private final NativeWebRequest request;

    private final UserService userService;
    private final PathService pathService;
    private final GeometryFactory geometryFactory;


    @org.springframework.beans.factory.annotation.Autowired
    public PathsApiController(NativeWebRequest request, UserService userService, PathService pathService, GeometryFactory geometryFactory, ObjectMapper objectMapper) {
        super(objectMapper);
        this.request = request;
        this.userService = userService;
        this.pathService = pathService;
        this.geometryFactory = geometryFactory;
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

        Path path = PathDTOMapper.INSTANCE.toPathEntity(walkingRouteDto, geometryFactory,userService.getUserById(walkingRouteDto.getCreator().getId()));
        pathService.savePath(path);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Override
    public ResponseEntity<Void> deletePath(Long pathId) throws Exception {
        pathService.deletePath(pathId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<WalkingRouteDto>> getPaths(@NotNull @Valid @RequestParam("filter") AreaFilterDto filter) throws Exception {
        filter.addVisibleAreaItem(filter.getVisibleArea().get(0));
        Polygon areaFilterPolygon = geometryFactory.createPolygon(SpatialDTOMapper.INSTANCE.getCoordinates(filter.getVisibleArea()));
        List<Path> pathInPolygon = pathService.getAllPathsInArea(areaFilterPolygon);
        return ResponseEntity.ok(PathDTOMapper.INSTANCE.toWalkingRouteDtoList(pathInPolygon));
    }
}
