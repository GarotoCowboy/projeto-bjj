package br.com.pedro.sistema.gestao.pagamento.usecases.student;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ResponsibleException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveStudentUseCaseTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @InjectMocks
    private SaveStudentUseCase saveStudentUseCase;

    @Test
    @DisplayName("Should save student successfully when no responsible is required")
    void shouldSaveStudentWithoutResponsible() {
        LocalDate birthday = LocalDate.now().minusYears(20);
        Person person = new Person("Adult", "adult@test.com", "11999999999", birthday);
        Student student = new Student(person, 75, 175, false, null, null);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = saveStudentUseCase.execute(student);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(student);
        verifyNoInteractions(responsibleRepository);
    }

    @Test
    @DisplayName("Should throw exception when student is null")
    void shouldThrowExceptionWhenStudentIsNull() {
        assertThrows(StudentException.class, () -> saveStudentUseCase.execute(null));
    }

    @Test
    @DisplayName("Should throw ResponsibleException when responsible is not found in repository")
    void shouldThrowExceptionWhenResponsibleNotFound() {
        LocalDate birthday = LocalDate.now().minusYears(15);
        Person studentPerson = new Person("Minor", "minor@test.com", "11999999999", birthday);
        Responsible respRef = new Responsible(99L, null, null, null, null, null, null);
        Student student = new Student(studentPerson, 50, 150, false, null, respRef);

        when(responsibleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponsibleException.class, () -> saveStudentUseCase.execute(student));
        verify(studentRepository, never()).save(any());
    }
}

