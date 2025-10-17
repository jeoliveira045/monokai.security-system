package monokai.securitysystem.repository;

import monokai.securitysystem.domain.OAuth2AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2AuthorizationRepository extends JpaRepository<OAuth2AuthorizationEntity, String> {
    
    Optional<OAuth2AuthorizationEntity> findByState(String state);
    
    Optional<OAuth2AuthorizationEntity> findByAuthorizationCodeValue(String authorizationCode);
    
    Optional<OAuth2AuthorizationEntity> findByAccessTokenValue(String accessToken);
    
    Optional<OAuth2AuthorizationEntity> findByRefreshTokenValue(String refreshToken);
    
    Optional<OAuth2AuthorizationEntity> findByOidcIdTokenValue(String idToken);
    
    Optional<OAuth2AuthorizationEntity> findByUserCodeValue(String userCode);
    
    Optional<OAuth2AuthorizationEntity> findByDeviceCodeValue(String deviceCode);
    
    @Query("SELECT a FROM OAuth2AuthorizationEntity a WHERE a.principalName = :principalName AND a.registeredClientId = :registeredClientId")
    Optional<OAuth2AuthorizationEntity> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(
            @Param("principalName") String principalName,
            @Param("registeredClientId") String registeredClientId);
}