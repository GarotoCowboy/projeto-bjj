package br.com.pedro.sistema.gestao.pagamento.usecases.instructor;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.InstructorException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;

public class FindInstructorByIdUseCase {

        private final InstructorRepository instructorRepository;

    public FindInstructorByIdUseCase(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

       public Instructor execute(Long id){
        if(id == null){
            throw new InstructorException(ErrorCode.GE_0001);
        }
        return instructorRepository.findById(id).orElseThrow(
            () -> new InstructorException(ErrorCode.IN_0007));
    }
}
