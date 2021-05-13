package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Tag;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.TagRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public TagService(TagRepository tagRepository, UserRepository userRepository, UserService userService) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Adds tag to owner's profile
     * @param tagToAdd tag object to add
     */
    @Transactional
    public void addTag(Tag tagToAdd){
        Set<Tag> tags = tagToAdd.getOwner().getTags();
        for (Tag tag : tags) {
            if (tag.getName().equals(tagToAdd.getName()) && tag.getTagType() == tagToAdd.getTagType()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tag is already added");
            }
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(tagToAdd.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to manipulate this tag");
        }
        this.tagRepository.saveAndFlush(tagToAdd);
    }

    /**
     * Deletes a tag with provided id
     * @param userId the id of the user that deletes the tag
     * @param tagId the tagId of tag to be deleted
     */
    @Transactional
    public void deleteTag(Long tagId, Long userId){
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with provided id exists");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,  "User is not permitted to delete another user tags");
        }
        Optional<Tag> optionalTag = this.tagRepository.findById(tagId);
        if(optionalTag.isPresent()){
            if(optionalTag.get().getOwner().getId().equals(userId)){
                tagRepository.delete(optionalTag.get());
                return;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not permitted to delete another user tags");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no Tag with provided id exists");
    }
}
