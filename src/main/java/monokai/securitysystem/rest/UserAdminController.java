package monokai.securitysystem.rest;

import lombok.Data;
import monokai.securitysystem.domain.User;
import monokai.securitysystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;


@RestController
@RequestMapping("/api/users")
public class UserAdminController {


    private final UserService userService;


    public UserAdminController(UserService userService) {
this.userService = userService;
}


    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequest req) {
        User u = userService.createUser(req.getUsername(), req.getPassword(), req.getEmail());
        return ResponseEntity.created(URI.create("/api/users/" + u.getId())).build();
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