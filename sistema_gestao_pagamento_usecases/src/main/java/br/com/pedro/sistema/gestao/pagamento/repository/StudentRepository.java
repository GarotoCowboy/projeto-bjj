package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.Student;

public interface StudentRepository {

    Student save(Student student);

    Student update(Long id, Student student);

    Student inactiveStudent(Long id);
    Student activeStudent(Long id);

    Optional<Student> findById(Long id);

    List<Student> findAll();

    List<Student> findAllActive();
}

