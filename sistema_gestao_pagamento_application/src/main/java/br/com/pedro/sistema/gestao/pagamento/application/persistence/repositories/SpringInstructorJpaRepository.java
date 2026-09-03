package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringInstructorJpaRepository extends JpaRepository<InstructorEntity, Long> {

    Optional<InstructorEntity> findByUsername(String username);
}
