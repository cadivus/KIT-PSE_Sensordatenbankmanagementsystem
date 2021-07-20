package notificationsystem.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
