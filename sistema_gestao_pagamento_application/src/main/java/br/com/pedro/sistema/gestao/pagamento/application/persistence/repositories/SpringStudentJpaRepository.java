package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringStudentJpaRepository extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> findByIsActiveTrue();
    List<StudentEntity> findByResponsibleId(Long responsibleId);
    long countByResponsibleId(Long responsibleId);
}
