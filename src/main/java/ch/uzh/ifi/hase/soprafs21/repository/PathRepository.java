package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Path;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository  extends JpaRepository<Path,Long> {

    @Query("select p from Path p where within(p.route , ?1) = true")
    List<Path> findByArea(Polygon areaFilterPolygon);
}
