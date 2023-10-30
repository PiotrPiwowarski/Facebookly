package pl.piwowarski.facebookly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
