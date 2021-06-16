package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    T get(T t);
    List<T> getAll();
    void save(T t);
    void delete(T t);

}
