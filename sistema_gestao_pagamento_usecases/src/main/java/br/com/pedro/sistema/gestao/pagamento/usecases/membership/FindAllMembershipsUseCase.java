package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;

public class FindAllMembershipsUseCase {
        private final MembershipRepository membershipRepository;

    public FindAllMembershipsUseCase(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
    }

    public List<Membership> execute(){

        return membershipRepository.findAll();
    }
}
