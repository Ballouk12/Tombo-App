package tomo.ma.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomo.ma.model.AUser;
@Repository
public interface UserRepository extends JpaRepository<AUser, Long> {
    AUser findByEmail(String email);
}
