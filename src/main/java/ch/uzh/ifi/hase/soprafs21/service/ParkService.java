package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ParkRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ParkService {

    private final ParkRepository parkRepository;

    private final UserService userService;

    @Autowired
    public ParkService(ParkRepository parkRepository, UserService userService) {
        this.parkRepository = parkRepository;
        this.userService = userService;
    }

    @Transactional
    public Park addPark(Park parkToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(parkToAdd.getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to delete another user parks");
        }
        return this.parkRepository.saveAndFlush(parkToAdd);
    }

    public List<Park> getAllParksInArea(Polygon areaFilterPolygon){
        return this.parkRepository.findByArea(areaFilterPolygon);
    }

    @Transactional
    public void deletePark(Long parkId){
        Optional<Park> optionalPark = this.parkRepository.findById(parkId);
        if(optionalPark.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Park with provided id exists");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(optionalPark.get().getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,  "User is not permitted to delete another user parks");
        }
        this.parkRepository.delete(optionalPark.get());
    }

}
