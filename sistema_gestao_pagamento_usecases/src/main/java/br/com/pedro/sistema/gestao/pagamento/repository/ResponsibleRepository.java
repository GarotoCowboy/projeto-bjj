package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;

public interface ResponsibleRepository {

    Responsible save(Responsible responsible);

    Optional<Responsible> findById(Long id);

    List<Responsible> findAll();
}

