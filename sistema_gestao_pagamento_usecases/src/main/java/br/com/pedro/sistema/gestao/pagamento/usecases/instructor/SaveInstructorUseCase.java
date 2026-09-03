package br.com.pedro.sistema.gestao.pagamento.usecases.instructor;

import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;

public class SaveInstructorUseCase {

    private final InstructorRepository instructorRepository;

    public SaveInstructorUseCase(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public Instructor execute(Instructor instructor){
        return instructorRepository.save(instructor);
    }

}
