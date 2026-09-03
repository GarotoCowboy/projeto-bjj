package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

public interface GraduationRepository {

    Graduation save(Graduation graduation);

    Optional<Graduation> findById(Long id);

    Optional<Graduation> findByBeltAndDegree(Belt belt, int degree);

    List<Graduation> findAll();
}

