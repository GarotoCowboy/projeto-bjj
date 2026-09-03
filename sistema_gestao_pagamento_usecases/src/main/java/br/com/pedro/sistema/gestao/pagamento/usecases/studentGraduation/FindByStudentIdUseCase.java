package br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentGraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentGraduationRepository;

public class FindByStudentIdUseCase {

            private final StudentGraduationRepository studentGraduationRepository;

    public FindByStudentIdUseCase(StudentGraduationRepository studentGraduationRepository){
        this.studentGraduationRepository = studentGraduationRepository;
    }

    public List<StudentGraduation> execute(Long studentId){
        
          if(studentId == null){
            throw new StudentGraduationException(ErrorCode.GE_0001);
        }

        return studentGraduationRepository.findByStudentId(studentId);
    }
}
