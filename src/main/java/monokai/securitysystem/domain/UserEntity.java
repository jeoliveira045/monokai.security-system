package monokai.securitysystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(unique = true)
    private String email;


    @Column(name = "password_hash", nullable = false)
    private String passwordHash;


    private boolean enabled = true;


    private boolean mfaEnabled = false;


    private String mfaSecret;


    private OffsetDateTime createdAt;


    private OffsetDateTime updatedAt;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


// getters/setters omitted for brevity
}
