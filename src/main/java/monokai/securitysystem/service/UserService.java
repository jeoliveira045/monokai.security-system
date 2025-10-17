package monokai.securitysystem.service;

import jakarta.persistence.EntityNotFoundException;
import monokai.securitysystem.domain.UserEntity;
import monokai.securitysystem.domain.dto.CustomUserDetails;
import monokai.securitysystem.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity createUser(String username, String rawPassword, String email) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        return userEntityRepository.save(u);
    }


    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userEntityRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new EntityNotFoundException("Usuário não encontrado!");
        }

        return new CustomUserDetails(user.get());
    }


//    public boolean verifyPassword(User user, String rawPassword) {
//        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
//    }
}
