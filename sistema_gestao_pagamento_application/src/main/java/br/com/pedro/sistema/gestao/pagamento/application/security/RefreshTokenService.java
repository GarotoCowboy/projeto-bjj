package br.com.pedro.sistema.gestao.pagamento.application.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.RefreshTokenEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringRefreshTokenJpaRepository;
import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
    
    @Value("${jwt.refresh-expiration-days:30}")
    private Long refreshExpirationDays;
    private final SpringRefreshTokenJpaRepository refreshTokenRepository;
    public RefreshTokenService(SpringRefreshTokenJpaRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Transactional
    public RefreshTokenEntity createRefreshToken(String username) {
      
        refreshTokenRepository.deleteByUsername(username);
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .username(username)
                .token(UUID.randomUUID().toString()) 
                .expiryDate(Instant.now().plus(refreshExpirationDays, ChronoUnit.DAYS))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request.");
        }
        return token;
    }
}