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
    public Tag addTag(Tag tagToAdd){
        if (!userService.isRequesterAndAuthenticatedUserTheSame(tagToAdd.getOwner().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to manipulate this tag");
        }
        Long userId = tagToAdd.getOwner().getId();
        Optional<User> optionalUser = Optional.of(userRepository.findById(userId).get());
        if (optionalUser.isPresent()){
            Set<Tag> tags = optionalUser.get().getTags();
            if (tags != null){
                for (Tag tag : tags) {
                    if (tag.getName().equalsIgnoreCase(tagToAdd.getName()) && tag.getTagType() == tagToAdd.getTagType()){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag is already added");
                    }
                }
            }
        }
        return this.tagRepository.saveAndFlush(tagToAdd);
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
        if(!optionalTag.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Tag with provided id exists");
        }
        if(!optionalTag.get().getOwner().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to delete another user tags");
        }
        tagRepository.delete(optionalTag.get());
    }
}
