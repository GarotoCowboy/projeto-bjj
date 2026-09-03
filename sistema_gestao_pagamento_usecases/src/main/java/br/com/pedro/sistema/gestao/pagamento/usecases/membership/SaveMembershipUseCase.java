package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class SaveMembershipUseCase {

    private final MembershipRepository membershipRepository;
    private final StudentRepository studentRepository;

    public SaveMembershipUseCase(MembershipRepository membershipRepository, StudentRepository studentRepository) {
        this.membershipRepository = membershipRepository;
        this.studentRepository = studentRepository;
    }

    public Membership execute(Membership membership) {
        if (membership == null) {
            throw new MembershipException(ErrorCode.MB_0001);
        }

        if (membership.getStudent() == null || membership.getStudent().getId() == null) {
            throw new MembershipException(ErrorCode.MB_0001);
        }

        Student student = studentRepository.findById(membership.getStudent().getId())
                .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));

        membership.setStudent(student);
        return membershipRepository.save(membership);
    }
}
