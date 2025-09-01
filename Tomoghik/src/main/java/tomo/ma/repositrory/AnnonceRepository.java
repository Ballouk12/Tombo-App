package tomo.ma.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomo.ma.model.Annonce;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    @Query("SELECT a FROM Annonce a WHERE a.user.id = :userId")
    List<Annonce> findByUserId(@Param("userId") Long userId);
}
