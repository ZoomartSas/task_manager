package kg.test.task_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private String username;

    private String password;

    private String role;

    private boolean enabled = true;
}
