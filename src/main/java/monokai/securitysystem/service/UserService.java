package monokai.securitysystem.service;

import monokai.securitysystem.domain.UserEntity;
import monokai.securitysystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserEntity createUser(String username, String rawPassword, String email) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(rawPassword);
        return userRepository.save(u);
    }


    public Optional<UserEntity> findByUsername(String username) {
    return userRepository.findByUsername(username);
    }


//    public boolean verifyPassword(User user, String rawPassword) {
//        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
//    }
}
