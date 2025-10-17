package monokai.securitysystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "oauth2_authorization")
@Data
public class OAuth2AuthorizationEntity {
    
    @Id
    private String id;
    
    @Column(name = "registered_client_id", length = 100, nullable = false)
    private String registeredClientId;
    
    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;
    
    @Column(name = "authorization_grant_type", length = 100, nullable = false)
    private String authorizationGrantType;
    
    @Lob
    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;
    
    @Lob
    @Column(name = "attributes", length = 4000)
    private String attributes;
    
    @Column(name = "state", length = 500)
    private String state;
    
    @Lob
    @Column(name = "authorization_code_value")
    private String authorizationCodeValue;
    
    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;
    
    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;
    
    @Lob
    @Column(name = "authorization_code_metadata", length = 2000)
    private String authorizationCodeMetadata;
    
    @Lob
    @Column(name = "access_token_value")
    private String accessTokenValue;
    
    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;
    
    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;
    
    @Lob
    @Column(name = "access_token_metadata", length = 2000)
    private String accessTokenMetadata;
    
    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;
    
    @Lob
    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;
    
    @Lob
    @Column(name = "refresh_token_value")
    private String refreshTokenValue;
    
    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;
    
    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;
    
    @Lob
    @Column(name = "refresh_token_metadata", length = 2000)
    private String refreshTokenMetadata;
    
    @Lob
    @Column(name = "oidc_id_token_value")
    private String oidcIdTokenValue;
    
    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;
    
    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;
    
    @Lob
    @Column(name = "oidc_id_token_metadata", length = 2000)
    private String oidcIdTokenMetadata;
    
    @Lob
    @Column(name = "oidc_id_token_claims", length = 2000)
    private String oidcIdTokenClaims;
    
    @Lob
    @Column(name = "user_code_value")
    private String userCodeValue;
    
    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;
    
    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;
    
    @Lob
    @Column(name = "user_code_metadata", length = 2000)
    private String userCodeMetadata;
    
    @Lob
    @Column(name = "device_code_value")
    private String deviceCodeValue;
    
    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;
    
    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;
    
    @Lob
    @Column(name = "device_code_metadata", length = 2000)
    private String deviceCodeMetadata;

    // Construtores, getters e setters
    public OAuth2AuthorizationEntity() {}
    
    // Getters e Setters (omitidos por brevidade, mas necessários)
    // ... todos os getters e setters para os campos acima
}