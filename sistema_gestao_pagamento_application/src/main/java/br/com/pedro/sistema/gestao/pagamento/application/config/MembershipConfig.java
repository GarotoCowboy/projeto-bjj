package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.membership.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MembershipConfig {

    @Bean
    public SaveMembershipUseCase saveMembershipUseCase(MembershipRepository membershipRepository, StudentRepository studentRepository) {
        return new SaveMembershipUseCase(membershipRepository, studentRepository);
    }

    @Bean
    public FindAllMembershipsUseCase findAllMembershipsUseCase(MembershipRepository membershipRepository) {
        return new FindAllMembershipsUseCase(membershipRepository);
    }

    @Bean
    public FindMembershipByIdUseCase findMembershipByIdUseCase(MembershipRepository membershipRepository) {
        return new FindMembershipByIdUseCase(membershipRepository);
    }

    @Bean
    public FindMembershipByStatusUseCase findMembershipByStatusUseCase(MembershipRepository membershipRepository) {
        return new FindMembershipByStatusUseCase(membershipRepository);
    }

    @Bean
    public FindMembershipByStudentIdUseCase findMembershipByStudentIdUseCase(MembershipRepository membershipRepository) {
        return new FindMembershipByStudentIdUseCase(membershipRepository);
    }

    @Bean
    public PayMembershipUseCase payMembershipUseCase(MembershipRepository membershipRepository) {
        return new PayMembershipUseCase(membershipRepository);
    }

    @Bean
    public CancelMembershipUseCase cancelMembershipUseCase(MembershipRepository membershipRepository) {
        return new CancelMembershipUseCase(membershipRepository);
    }
}
