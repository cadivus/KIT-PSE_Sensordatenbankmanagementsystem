package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id);
    List<T> getAll();

}
