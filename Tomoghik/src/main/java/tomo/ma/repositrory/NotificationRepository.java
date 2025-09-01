package tomo.ma.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomo.ma.model.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n JOIN n.alert a WHERE a.user.id = :userId")
    List<Notification> findByUserId(@Param("userId") long userId);
    @Query("SELECT n FROM Notification n JOIN n.alert a WHERE a.user.id = :userId AND n.state = tomo.ma.dto.State.UNREAD")
    List<Notification> findUnReadByUserId(@Param("userId") long userId);
}
