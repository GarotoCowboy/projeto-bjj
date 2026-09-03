package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

public class FindMembershipByIdUseCase {
        private final MembershipRepository membershipRepository;

    public FindMembershipByIdUseCase(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    public Membership execute(Long id){
        if(id == null){
            throw new MembershipException(ErrorCode.GE_0001);
        }

        return membershipRepository.findById(id).orElseThrow(
            () -> new MembershipException(ErrorCode.MB_0010)
        );
    }
}
