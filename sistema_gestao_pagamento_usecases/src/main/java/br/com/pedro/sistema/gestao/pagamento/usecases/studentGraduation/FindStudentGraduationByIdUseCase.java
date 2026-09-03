package br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentGraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentGraduationRepository;

public class FindStudentGraduationByIdUseCase {

        private final StudentGraduationRepository studentGraduationRepository;

    public FindStudentGraduationByIdUseCase(StudentGraduationRepository studentGraduationRepository){
        this.studentGraduationRepository = studentGraduationRepository;
    }

    public StudentGraduation execute(Long id){
        if(id == null){
            throw new StudentGraduationException(ErrorCode.GE_0001);
        }

        return studentGraduationRepository.findById(id).orElseThrow(
            () -> new StudentGraduationException(ErrorCode.SG_0005)
        );
    }
}
