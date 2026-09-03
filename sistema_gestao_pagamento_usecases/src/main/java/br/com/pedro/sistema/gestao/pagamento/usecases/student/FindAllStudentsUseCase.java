package br.com.pedro.sistema.gestao.pagamento.usecases.student;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class FindAllStudentsUseCase {

        private final StudentRepository studentRepository;

    public FindAllStudentsUseCase(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> execute(){
        return studentRepository.findAll();
    }
}
