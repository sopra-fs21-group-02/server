package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.Park;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ParkRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ParkService {

    private final ParkRepository parkRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public ParkService(ParkRepository parkRepository, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.parkRepository = parkRepository;
        this.userService = userService;
    }

    @Transactional
    public Park addPark(Park parkToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(parkToAdd.getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to manipulate this park");
        }
        return this.parkRepository.saveAndFlush(parkToAdd);
    }

}
