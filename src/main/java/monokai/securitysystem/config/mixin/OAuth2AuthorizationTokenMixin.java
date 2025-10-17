package monokai.securitysystem.config.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.util.Map;

// Mixin para OAuth2Authorization.Token
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
public abstract class OAuth2AuthorizationTokenMixin {

    @JsonCreator
    OAuth2AuthorizationTokenMixin(
            @JsonProperty("token") OAuth2Token token,
            @JsonProperty("metadata") Map<String, Object> metadata
    ) {
    }
}
