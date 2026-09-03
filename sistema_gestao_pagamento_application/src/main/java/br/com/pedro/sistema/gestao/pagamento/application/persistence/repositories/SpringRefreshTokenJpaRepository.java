package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.RefreshTokenEntity;

@Repository
public interface SpringRefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUsername(String username);
}