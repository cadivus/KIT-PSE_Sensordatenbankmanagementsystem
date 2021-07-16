package notificationsystem.model;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.sql.SQLException;
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

    /**
     * Gets an object from the database.
     * @param t identifier for the object needed.
     * @return The object fetched.
     */
    Optional<T> get(T t);

    /**
     * Gets a list of all the objects of the given class from the database.
     * @return List of all the objects of that kind in the database.
     */
    List<T> getAll();

}
