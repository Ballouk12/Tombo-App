package tomo.ma.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomo.ma.model.Defect;
@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {
}
