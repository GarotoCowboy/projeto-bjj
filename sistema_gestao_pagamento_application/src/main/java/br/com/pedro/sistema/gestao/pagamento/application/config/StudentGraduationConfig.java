package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentGraduationRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindByInstructorIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindByStudentIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindStudentGraduationByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.SaveStudentGraduationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentGraduationConfig {

    @Bean
    public SaveStudentGraduationUseCase saveStudentGraduationUseCase(
            StudentGraduationRepository studentGraduationRepository,
            StudentRepository studentRepository,
            InstructorRepository instructorRepository,
            GraduationRepository graduationRepository) {
        return new SaveStudentGraduationUseCase(
                studentGraduationRepository,
                studentRepository,
                instructorRepository,
                graduationRepository
        );
    }

    @Bean
    public FindStudentGraduationByIdUseCase findStudentGraduationByIdUseCase(StudentGraduationRepository studentGraduationRepository) {
        return new FindStudentGraduationByIdUseCase(studentGraduationRepository);
    }

    @Bean
    public FindByStudentIdUseCase findByStudentIdUseCase(StudentGraduationRepository studentGraduationRepository) {
        return new FindByStudentIdUseCase(studentGraduationRepository);
    }

    @Bean
    public FindByInstructorIdUseCase findByInstructorIdUseCase(StudentGraduationRepository studentGraduationRepository) {
        return new FindByInstructorIdUseCase(studentGraduationRepository);
    }
}
