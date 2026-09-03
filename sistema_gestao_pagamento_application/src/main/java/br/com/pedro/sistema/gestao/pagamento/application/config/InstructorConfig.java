package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindAllInstructorsUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindInstructorByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindInstructorByUsernameUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.SaveInstructorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstructorConfig {

    @Bean
    public SaveInstructorUseCase saveInstructorUseCase(InstructorRepository instructorRepository) {
        return new SaveInstructorUseCase(instructorRepository);
    }

    @Bean
    public FindAllInstructorsUseCase findAllInstructorsUseCase(InstructorRepository instructorRepository) {
        return new FindAllInstructorsUseCase(instructorRepository);
    }

    @Bean
    public FindInstructorByIdUseCase findInstructorByIdUseCase(InstructorRepository instructorRepository) {
        return new FindInstructorByIdUseCase(instructorRepository);
    }

    @Bean
    public FindInstructorByUsernameUseCase findInstructorByUsernameUseCase(InstructorRepository instructorRepository) {
        return new FindInstructorByUsernameUseCase(instructorRepository);
    }
}
