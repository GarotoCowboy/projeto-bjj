package br.com.pedro.sistema.gestao.pagamento.repository;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;

public interface MembershipRepository {

    Membership save(Membership membership);

    Optional<Membership> findById(Long id);

    List<Membership> findByStudentId(Long studentId);

    List<Membership> findByStatus(PaymentStatus status);

    List<Membership> findAll();
}

