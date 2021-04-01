package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ImageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);
}
