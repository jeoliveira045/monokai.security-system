package monokai.securitysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use factory methods ou construtor e ajuste parâmetros conforme sua infra
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}