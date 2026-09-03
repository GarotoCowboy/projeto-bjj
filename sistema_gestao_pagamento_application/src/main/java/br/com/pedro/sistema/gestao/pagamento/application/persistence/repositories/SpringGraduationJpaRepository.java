package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.GraduationEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringGraduationJpaRepository extends JpaRepository<GraduationEntity, Long> {

    Optional<GraduationEntity> findByBeltAndDegree(Belt belt, int degree);
}
