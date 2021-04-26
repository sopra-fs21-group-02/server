package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class DogService {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Dog addDog(Dog dogToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(dogToAdd.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return this.dogRepository.saveAndFlush(dogToAdd);
    }

}
