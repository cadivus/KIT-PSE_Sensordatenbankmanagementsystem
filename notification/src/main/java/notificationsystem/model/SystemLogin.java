package notificationsystem.model;

import javax.persistence.*;

/**
 * Bundles up information needed to log in to a system e-mail account. Mapped to a database table storing that information.
 */
@Entity(name = "SystemLogin")
@Table(name = "systemlogin")
public class SystemLogin {

    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "username",
            nullable = false
    )
    private String username;

    public SystemLogin() {}

    /**
     * Gets id of the login credentials.
     * @return id of the login credentials.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the password of the login credentials.
     * @return password of the login credentials.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the username of the login credentials.
     * @return username of the login credentials.
     */
    public String getUsername() {
        return username;
    }
}
