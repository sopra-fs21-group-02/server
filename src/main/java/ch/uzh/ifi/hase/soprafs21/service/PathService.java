package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PathService {

    private final PathRepository pathRepository;

    private final UserService userService;

    @Autowired
    public PathService(PathRepository pathRepository, UserService userService) {
        this.pathRepository = pathRepository;
        this.userService = userService;
    }

    @Transactional
    public Path savePath(Path path) {
        return this.pathRepository.saveAndFlush(path);
    }

    @Transactional
    public void deletePath(Long pathId) {

        Optional<Path> optionalPath = this.pathRepository.findById(pathId);
        if(optionalPath.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Path with provided id exists");
        }
        if (!userService.isRequesterAndAuthenticatedUserTheSame(optionalPath.get().getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,  "User is not permitted to delete another user paths");
        }
        this.pathRepository.delete(optionalPath.get());
    }
}
