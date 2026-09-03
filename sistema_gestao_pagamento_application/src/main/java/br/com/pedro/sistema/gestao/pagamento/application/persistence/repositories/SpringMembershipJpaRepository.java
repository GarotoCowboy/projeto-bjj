package br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.MembershipEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringMembershipJpaRepository extends JpaRepository<MembershipEntity, Long> {

    List<MembershipEntity> findByStudentId(Long studentId);

    List<MembershipEntity> findByStatus(PaymentStatus status);
}
