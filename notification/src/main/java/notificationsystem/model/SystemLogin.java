package notificationsystem.model;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
