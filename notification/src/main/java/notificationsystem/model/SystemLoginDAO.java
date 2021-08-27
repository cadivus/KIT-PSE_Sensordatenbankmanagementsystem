package notificationsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The SystemLoginDAO class handles database queries regarding the log in data for the system e-mail. In this case, it
 * only ever needs to get the saved data.
 */
@Service
public class SystemLoginDAO {

    private final SystemLoginRepository systemLoginRepository;

    @Autowired
    public SystemLoginDAO(SystemLoginRepository systemLoginRepository) {
        this.systemLoginRepository = systemLoginRepository;
    }

    /**
     * Gets login data saved under the given id from the database.
     * @param id id of the login data required.
     * @return id, username and password bundled in an instance of the SystemLogin class.
     */
    public Optional<SystemLogin> getLogin(Long id) {
        return systemLoginRepository.findById(id);
    }
}
