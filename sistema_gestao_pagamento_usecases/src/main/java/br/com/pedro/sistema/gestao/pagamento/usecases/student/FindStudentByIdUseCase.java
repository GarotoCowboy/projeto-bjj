package br.com.pedro.sistema.gestao.pagamento.usecases.student;


import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class FindStudentByIdUseCase {

    private final StudentRepository studentRepository;

    public FindStudentByIdUseCase(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public Student execute(Long id){
        if(id == null){
            throw new StudentException(ErrorCode.GE_0001);
        }
        return studentRepository.findById(id).orElseThrow(
            () -> new StudentException(ErrorCode.ST_0006));
    }

}
