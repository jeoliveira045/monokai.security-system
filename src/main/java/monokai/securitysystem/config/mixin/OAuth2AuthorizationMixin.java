package monokai.securitysystem.config.mixin;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.util.Map;
import java.util.Set;

// Mixin para OAuth2Authorization
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AuthorizationMixin {
    
    @JsonCreator
    OAuth2AuthorizationMixin(
        @JsonProperty("registeredClientId") String registeredClientId,
        @JsonProperty("principalName") String principalName,
        @JsonProperty("authorizationGrantType") AuthorizationGrantType authorizationGrantType,
        @JsonProperty("authorizedScopes") Set<String> authorizedScopes,
        @JsonProperty("tokens") Map<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> tokens,
        @JsonProperty("attributes") Map<String, Object> attributes
    ) {}
}

