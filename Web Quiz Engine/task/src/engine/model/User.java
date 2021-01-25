package engine.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(regexp = "^(.+)@(.+)\\.(.+)$")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 5)
    private String password;

    public User() {
    }

    public User(@Email(regexp = "^(.+)@(.+)\\.(.+)$") String email, @Size(min = 5) String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
