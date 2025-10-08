package monokai.securitysystem.rest;

import jakarta.annotation.security.RolesAllowed;
import lombok.Data;
import monokai.securitysystem.domain.UserEntity;
import monokai.securitysystem.repository.UserRepository;
//import monokai.securitysystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.net.URI;


@RestController
@RequestMapping("/api/users")
public class UserAdminController {


//    private final UserService userService;
    private final UserRepository userRepository;


    public UserAdminController( UserRepository userRepository) {
//        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequest req) {
//        UserEntity u = userService.createUser(req.getUsername(), req.getPassword(), req.getEmail());
        return ResponseEntity.created(URI.create("/api/users/")).build();
    }


// DTO
    @Data
    public static class CreateUserRequest {
        private String username;
        private String password;
        private String email;
        // getters/setters
    }
}
