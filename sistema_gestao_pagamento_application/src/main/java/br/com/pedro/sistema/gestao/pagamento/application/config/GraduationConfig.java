package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindAllGraduationsUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindGraduationByBeltAndDegreeUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindGraduationByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.SaveGraduationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraduationConfig {

    @Bean
    public SaveGraduationUseCase saveGraduationUseCase(GraduationRepository graduationRepository) {
        return new SaveGraduationUseCase(graduationRepository);
    }

    @Bean
    public FindAllGraduationsUseCase findAllGraduationsUseCase(GraduationRepository graduationRepository) {
        return new FindAllGraduationsUseCase(graduationRepository);
    }

    @Bean
    public FindGraduationByIdUseCase findGraduationByIdUseCase(GraduationRepository graduationRepository) {
        return new FindGraduationByIdUseCase(graduationRepository);
    }

    @Bean
    public FindGraduationByBeltAndDegreeUseCase findGraduationByBeltAndDegreeUseCase(GraduationRepository graduationRepository) {
        return new FindGraduationByBeltAndDegreeUseCase(graduationRepository);
    }
}
