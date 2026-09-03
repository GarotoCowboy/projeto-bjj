package br.com.pedro.sistema.gestao.pagamento.usecases.instructor;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.InstructorException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;

public class FindInstructorByUsernameUseCase {

            private final InstructorRepository instructorRepository;

    public FindInstructorByUsernameUseCase(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

       public Instructor execute(String username){
        if(username == null || username.trim().isEmpty()){
            throw new InstructorException(ErrorCode.GE_0002);
        }
        return instructorRepository.findByUsername(username).orElseThrow(
            () -> new InstructorException(ErrorCode.IN_0008));
    }
}
