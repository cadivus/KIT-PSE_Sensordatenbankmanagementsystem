package notificationsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemLoginDAO {

    private SystemLoginRepository systemLoginRepository;

    @Autowired
    public SystemLoginDAO(SystemLoginRepository systemLoginRepository) {
        this.systemLoginRepository = systemLoginRepository;
    }

    public Optional<SystemLogin> getLogin(Long id) {
        return systemLoginRepository.findById(id);
    }
}
