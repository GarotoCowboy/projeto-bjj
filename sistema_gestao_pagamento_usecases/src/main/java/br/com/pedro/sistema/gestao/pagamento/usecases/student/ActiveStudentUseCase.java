package br.com.pedro.sistema.gestao.pagamento.usecases.student;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class ActiveStudentUseCase {

         private final StudentRepository studentRepository;

    public ActiveStudentUseCase(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public Student execute(Long id){

        if(id == null){
            throw new StudentException(ErrorCode.GE_0001);
        }

        Student existingStudent = studentRepository.findById(id)
        .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));

        if(existingStudent.getIsActive()){
            throw new StudentException(ErrorCode.ST_0008);
        }

        existingStudent.setIsActive(true);

        return studentRepository.save(existingStudent);

    }
}

