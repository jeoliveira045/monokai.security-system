package monokai.securitysystem.domain.dto;

import monokai.securitysystem.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<? extends GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(UserEntity userEntity){
        this.username = userEntity.getUsername();
        this.password = userEntity.getPasswordHash();
        this.grantedAuthorities = userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
