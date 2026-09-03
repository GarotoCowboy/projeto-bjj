package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;

public interface StudentGraduationRepository {

    StudentGraduation save(StudentGraduation studentGraduation);

    Optional<StudentGraduation> findById(Long id);

    List<StudentGraduation> findByStudentId(Long studentId);

    List<StudentGraduation> findByInstructorId(Long instructorId);
}

