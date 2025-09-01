package tomo.ma.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomo.ma.model.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
