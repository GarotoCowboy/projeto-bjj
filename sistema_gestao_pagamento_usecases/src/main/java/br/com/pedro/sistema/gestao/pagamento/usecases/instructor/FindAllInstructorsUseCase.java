package br.com.pedro.sistema.gestao.pagamento.usecases.instructor;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;

public class FindAllInstructorsUseCase {


            private final InstructorRepository instructorRepository;

    public FindAllInstructorsUseCase(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> execute(){
        return instructorRepository.findAll();
    }
}
