package monokai.securitysystem.config.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

// Mixin para OAuth2AuthorizationCode
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
public abstract class OAuth2AuthorizationCodeMixin {

    @JsonCreator
    OAuth2AuthorizationCodeMixin(
            @JsonProperty("tokenValue") String tokenValue,
            @JsonProperty("issuedAt") Instant issuedAt,
            @JsonProperty("expiresAt") Instant expiresAt
    ) {
    }
}
