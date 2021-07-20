package notificationsystem.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "LoginUtility")
public class SystemLogin {

    @Id
    private long id;
    @Convert(converter = Encryptor.class)
    private String username;
    @Convert(converter = Encryptor.class)
    private String password;

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
