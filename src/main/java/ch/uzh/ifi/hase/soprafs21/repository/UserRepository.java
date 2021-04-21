package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User set lastUserLocation = ?2 where id = ?1")
    void updateUserLocation(Long userId, Point newLocation);

    @Query("select u from User u where within(u.lastUserLocation, ?1) = true")
    List<User> findByArea(Polygon areaFilterPolygon);

    @Query("select u from User u where u.id <> ?1")
    List<User> findAll(Long userId);
}
