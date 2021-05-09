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

    @Autowired
    public PathService(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    @Transactional
    public void savePath(Path path) {
        pathRepository.saveAndFlush(path);
    }

    @Transactional
    public void deletePath(Long userId, Long pathId) {

        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if(optionalPath.isPresent()){
            Path path = optionalPath.get();
            if(userId.equals(path.getCreator().getId())){
                pathRepository.delete(path);
                pathRepository.flush();
            }else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not permitted to delete this Route");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Path id");
        }
    }
}
