package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class DogService {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Adds dog to owner's profile
     * @param dogToAdd dog object to add
     * @return added dog
     */
    @Transactional
    public Dog addDog(Dog dogToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(dogToAdd.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return this.dogRepository.saveAndFlush(dogToAdd);
    }

    /**
     * Deletes a dog with provided id
     * @param ownerId the id of the user that deletes the dog
     * @param dogId the id of dog to be deleted
     */
    @Transactional
    public void deleteDog(Long ownerId, Long dogId){
        Optional<User> optionalUser = this.userRepository.findById(ownerId);
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with provided id exists");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(ownerId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Optional<Dog> optionalDog = this.dogRepository.findById(dogId);
        if(optionalDog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Dog with provided id exists");
        }
        this.dogRepository.delete(optionalDog.get());
    }
}
