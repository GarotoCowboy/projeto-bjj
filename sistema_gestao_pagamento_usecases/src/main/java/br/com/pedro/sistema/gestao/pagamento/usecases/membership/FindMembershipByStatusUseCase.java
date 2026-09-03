package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

public class FindMembershipByStatusUseCase {

            private final MembershipRepository membershipRepository;

    public FindMembershipByStatusUseCase(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    public List<Membership> execute(PaymentStatus paymentStatus){

        if(paymentStatus == null){
            throw new MembershipException(ErrorCode.MB_0011);
        }

        return membershipRepository.findByStatus(paymentStatus);
    }

}
