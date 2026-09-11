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

        if (student.getPerson() != null && existingStudent.getPerson() != null) {
            if (student.getPerson().getName() != null && !student.getPerson().getName().trim().isEmpty()) {
                existingStudent.getPerson().setName(student.getPerson().getName());
            }
            if (student.getPerson().getEmail() != null && !student.getPerson().getEmail().trim().isEmpty()) {
                existingStudent.getPerson().setEmail(student.getPerson().getEmail());
            }
            if (student.getPerson().getPhoneNumber() != null && !student.getPerson().getPhoneNumber().trim().isEmpty()) {
                existingStudent.getPerson().setPhoneNumber(student.getPerson().getPhoneNumber());
            }
            if (student.getPerson().getBirthday() != null) {
                existingStudent.getPerson().setBirthday(student.getPerson().getBirthday());
            }
        }

        existingStudent.setHeight(student.getHeight());
        existingStudent.setWeight(student.getWeight());
        existingStudent.setIsHealthProblem(student.getIsHealthProblem());
        existingStudent.setHealthProblemDescription(student.getHealthProblemDescription());

        return studentRepository.save(existingStudent);
    }
}
