package br.com.pedro.sistema.gestao.pagamento.application.config;

import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import br.com.pedro.sistema.gestao.pagamento.usecases.student.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    public SaveStudentUseCase saveStudentUseCase(StudentRepository studentRepository, ResponsibleRepository responsibleRepository) {
        return new SaveStudentUseCase(studentRepository, responsibleRepository);
    }

    @Bean
    public ActiveStudentUseCase activeStudentUseCase(StudentRepository studentRepository) {
        return new ActiveStudentUseCase(studentRepository);
    }

    @Bean
    public FindAllStudentsUseCase findAllStudentsUseCase(StudentRepository studentRepository) {
        return new FindAllStudentsUseCase(studentRepository);
    }

    @Bean
    public FindAllActiveStudentsUseCase findAllActiveStudentsUseCase(StudentRepository studentRepository) {
        return new FindAllActiveStudentsUseCase(studentRepository);
    }

    @Bean
    public InactiveStudentUseCase inactiveStudentUseCase(StudentRepository studentRepository) {
        return new InactiveStudentUseCase(studentRepository);
    }

    @Bean
    public UpdateStudentUseCase updateStudentUseCase(StudentRepository studentRepository) {
        return new UpdateStudentUseCase(studentRepository);
    }

    @Bean
    public FindStudentByIdUseCase findStudentByIdUseCase(StudentRepository studentRepository) {
        return new FindStudentByIdUseCase(studentRepository);
    }
}
