package notificationsystem.model;

import javax.persistence.*;

@Entity(name = "LoginUtility")
@Table(name = "systemlogin")
public class SystemLogin {

    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private long id;
    @Column(
            name = "username",
            nullable = false
    )
    private String username;
    @Convert(converter = Encryptor.class)
    @Column(
            name = "password",
            nullable = false
    )
    private String password;

}
