package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

public class CancelMembershipUseCase {

    private final MembershipRepository membershipRepository;

    public CancelMembershipUseCase(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership execute(Long id) {
        if (id == null) {
            throw new MembershipException(ErrorCode.GE_0001);
        }

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new MembershipException(ErrorCode.MB_0010));

        membership.cancel();

        return membershipRepository.save(membership);
    }
}

