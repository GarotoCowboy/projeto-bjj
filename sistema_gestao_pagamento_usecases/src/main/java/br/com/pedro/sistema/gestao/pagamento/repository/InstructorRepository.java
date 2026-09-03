package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;

public interface InstructorRepository {

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(Long id);

    Optional<Instructor> findByUsername(String username);

    List<Instructor> findAll();
}

