package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
