package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Dog;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.DogRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class DogService {

    private final DogRepository dogRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public DogService(DogRepository dogRepository, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.dogRepository = dogRepository;
        this.userService = userService;
    }

    /**
     * Adds dog to owner's profile
     * @param dogToAdd dog object to add
     * @return added dog
     */
    @Transactional
    public Dog addDog(Dog dogToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(dogToAdd.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to manipulate this dog");
        }
        return this.dogRepository.saveAndFlush(dogToAdd);
    }

    public Dog getDogById(Long id) {
        return dogRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,  "User is not permitted to delete another user dogs");
        }
        Optional<Dog> optionalDog = this.dogRepository.findById(dogId);
        if(optionalDog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Dog with provided id exists");
        }
        this.dogRepository.delete(optionalDog.get());
    }

    /**
     * Updates the dog
     * @param dog dog object with modified fields
     * @return updated dog object
     */
    @Transactional
    public Dog editDog(Dog dog) {
        Optional<User> optionalUser = this.userRepository.findById(dog.getOwner().getId());
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with provided id exists");
        }
        Optional<Dog> optionalDog = dogRepository.findById(dog.getId());
        if(optionalDog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Dog with provided id exists");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(dog.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to edit another user dogs");
        }
        Dog dogToEdit = optionalDog.get();
        dogToEdit.setBreed(dog.getBreed());
        dogToEdit.setName(dog.getName());
        dogToEdit.setDateOfBirth(dog.getDateOfBirth());
        dogToEdit.setGender(dog.getGender());
        if(null != dog.getProfilePicture() && dog.getProfilePicture().length!=0){
            dogToEdit.setProfilePicture(dog.getProfilePicture());
            dogToEdit.setProfilePictureContentType(dog.getProfilePictureContentType());
        }
        return this.dogRepository.saveAndFlush(dogToEdit);
    }
}
