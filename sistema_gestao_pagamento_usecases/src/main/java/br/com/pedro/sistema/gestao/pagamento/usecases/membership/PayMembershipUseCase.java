package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

import java.time.LocalDate;

public class PayMembershipUseCase {

    private final MembershipRepository membershipRepository;

    public PayMembershipUseCase(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership execute(Long id, LocalDate paymentDate) {
        if (id == null) {
            throw new MembershipException(ErrorCode.GE_0001);
        }

        LocalDate effectivePaymentDate = (paymentDate != null) ? paymentDate : LocalDate.now();

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new MembershipException(ErrorCode.MB_0010));

        membership.markAsPaid(effectivePaymentDate);

        return membershipRepository.save(membership);
    }
}

