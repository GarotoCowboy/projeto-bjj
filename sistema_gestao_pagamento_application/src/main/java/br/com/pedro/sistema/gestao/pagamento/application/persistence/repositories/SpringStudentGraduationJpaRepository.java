package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentGraduationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringStudentGraduationJpaRepository extends JpaRepository<StudentGraduationEntity, Long> {

    List<StudentGraduationEntity> findByStudentId(Long studentId);

    List<StudentGraduationEntity> findByInstructorId(Long instructorId);
}
