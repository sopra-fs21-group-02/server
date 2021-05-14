package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.PathRepository;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Path> getAllPathsInArea(Polygon areaFilterPolygon) {
        return this.pathRepository.findByArea(areaFilterPolygon);
    }
}
