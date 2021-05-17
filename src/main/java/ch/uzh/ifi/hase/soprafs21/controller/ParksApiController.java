package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.AreaFilterDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.ParkDto;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DogDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.ParkDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.SpatialDTOMapper;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.UserDTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.*;
import io.swagger.annotations.ApiParam;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class ParksApiController implements ParksApi {

    private final NativeWebRequest request;

    private GeometryFactory geometryFactory;

    private final UserService userService;

    private final ParkService parkService;

    @Autowired
    public ParksApiController(NativeWebRequest request, UserService userService, ParkService parkService, GeometryFactory geometryFactory) {
        this.parkService = parkService;
        this.userService = userService;
        this.request = request;
        this.geometryFactory = geometryFactory;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> addPark(@ApiParam(value = "" ,required=true )  @Valid @RequestBody ParkDto parkDto) throws Exception {
        if (parkDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not allowed in POST");
        }
        User creator = userService.getUserById(parkDto.getCreatorId());
        Park parkToAdd = ParkDTOMapper.INSTANCE.toParkEntity(parkDto, creator, geometryFactory);

        parkService.addPark(parkToAdd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ParkDto>> getParks(@NotNull @ApiParam(value = "Filter to specify the visual area on the map", required = true) @Valid AreaFilterDto filter) throws Exception {
        filter.addVisibleAreaItem(filter.getVisibleArea().get(0)); //the first coordinate is added to have a closed polygon
        Polygon areaFilterPolygon = geometryFactory.createPolygon(SpatialDTOMapper.INSTANCE.getCoordinates(filter.getVisibleArea()));
        List<Park> parksInPolygon = parkService.getAllParksInArea(areaFilterPolygon);
        return ResponseEntity.ok(ParkDTOMapper.INSTANCE.toParkDTOList(parksInPolygon));
    }

    @Override
    public ResponseEntity<Void> deletePark(@ApiParam(value = "Numeric ID of the park",required=true) @PathVariable("parkId") Long parkId) throws Exception {
        parkService.deletePark(parkId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
