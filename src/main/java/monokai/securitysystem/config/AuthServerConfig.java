package monokai.securitysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.util.Objects;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthServerConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    @DependsOn("registeredClientRepository")
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity ,
            PasswordEncoder passwordEncoder,
            RegisteredClientRepository jdbcRegisteredClientRepository
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer auth2AuthorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        RegisteredClient registeredClient = RegisteredClient
                        .withId(UUID.randomUUID().toString())
                        .clientId("auth-server-web")
                        .clientSecret(passwordEncoder.encode("secret"))
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:8085/login/oauth2/code/client-web")
                        .redirectUri("http://localhost:8085/authorized")
                        .postLogoutRedirectUri("http://localhost:8085/")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope(OidcScopes.EMAIL)
                        .scope("read")
                        .scope("write")
                                .clientSettings(ClientSettings.builder().build())
                                        .build();

        if(Objects.isNull(jdbcRegisteredClientRepository.findByClientId("auth-server-web"))){
            jdbcRegisteredClientRepository.save(registeredClient);
        }


        httpSecurity
                .securityMatcher(auth2AuthorizationServerConfigurer.getEndpointsMatcher())
                .with(auth2AuthorizationServerConfigurer, (authorizationServer) -> {
                    authorizationServer.registeredClientRepository(jdbcRegisteredClientRepository);
                }).authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.ALL)
                ));

        return httpSecurity.formLogin(Customizer.withDefaults()).build();

    }
}
