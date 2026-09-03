package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

public class FindMembershipByStudentIdUseCase {
        private final MembershipRepository membershipRepository;

    public FindMembershipByStudentIdUseCase(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    public List<Membership> execute(Long studentId){

        if(studentId == null){
            throw new MembershipException(ErrorCode.GE_0001);
        }

        return membershipRepository.findByStudentId(studentId);
    }
}
