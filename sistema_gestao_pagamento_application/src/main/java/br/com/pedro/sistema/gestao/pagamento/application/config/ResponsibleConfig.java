package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.FindAllResponsiblesUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.FindResponsibleByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.SaveResponsibleUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponsibleConfig {

    @Bean
    public SaveResponsibleUseCase saveResponsibleUseCase(ResponsibleRepository responsibleRepository, StudentRepository studentRepository) {
        return new SaveResponsibleUseCase(responsibleRepository, studentRepository);
    }

    @Bean
    public FindAllResponsiblesUseCase findAllResponsiblesUseCase(ResponsibleRepository responsibleRepository) {
        return new FindAllResponsiblesUseCase(responsibleRepository);
    }

    @Bean
    public FindResponsibleByIdUseCase findResponsibleByIdUseCase(ResponsibleRepository responsibleRepository) {
        return new FindResponsibleByIdUseCase(responsibleRepository);
    }
}
