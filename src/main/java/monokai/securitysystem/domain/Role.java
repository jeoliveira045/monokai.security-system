package monokai.securitysystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue
    private UUID id;


    @Column(nullable = false, unique = true)
    private String name;


// getters/setters omitted
}
