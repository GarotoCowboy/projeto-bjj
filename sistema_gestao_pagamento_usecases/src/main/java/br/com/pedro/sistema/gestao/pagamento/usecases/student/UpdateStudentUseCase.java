package br.com.pedro.sistema.gestao.pagamento.usecases.student;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class UpdateStudentUseCase {


    private final StudentRepository studentRepository;

    public UpdateStudentUseCase(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student execute(Long id, Student student){

        if(id == null){
            throw new StudentException(ErrorCode.GE_0001);
        }
        if (student == null) {
            throw new StudentException(ErrorCode.ST_0001);
        }

       Student existingStudent = studentRepository.findById(id)
       .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));

        existingStudent.setHeight(student.getHeight());
        existingStudent.setWeight(student.getWeight());
        existingStudent.setIsHealthProblem(student.getIsHealthProblem());
        existingStudent.setHealthProblemDescription(student.getHealthProblemDescription());

        return studentRepository.save(existingStudent);
    }
}
