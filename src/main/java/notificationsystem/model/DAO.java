package notificationsystem.model;

import java.util.List;
import java.util.Optional;

/**
 * The DAO (Data-Access-Object) interface provides an interface with the basic get, save, delete and getAll methods.
 * The classes implementing it are used to hide away specific queries to the database ans subsequently simplify database
 * accesses.
 * @param <T> The interface as well as all methods use the basic T-parameter as different implementing classes are used
 *           to access different kinds of objects.
 */
public interface DAO<T> {

    T get(T t);
    List<T> getAll();
    void save(T t);
    void delete(T t);

}
