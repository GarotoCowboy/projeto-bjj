package br.com.pedro.sistema.gestao.pagamento.core.models;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    @DisplayName("Should create adult Student without Responsible")
    void shouldCreateAdultStudentWithoutResponsible() {
        LocalDate adultBirthday = LocalDate.now().minusYears(20);
        Person person = new Person("Adult Student", "adult@example.com", "11999999999", adultBirthday);

        Student student = new Student(person, 75, 175, false, null, null);

        assertNotNull(student);
        assertTrue(student.getIsActive());
        assertEquals(75, student.getWeight());
        assertEquals(175, student.getHeight());
        assertFalse(student.getIsHealthProblem());
        assertNull(student.getResponsible());
    }

    @Test
    @DisplayName("Should throw StudentException when minor Student has no Responsible")
    void shouldThrowExceptionWhenMinorHasNoResponsible() {
        LocalDate minorBirthday = LocalDate.now().minusYears(15);
        Person person = new Person("Minor Student", "minor@example.com", "11999999999", minorBirthday);

        assertThrows(StudentException.class, () -> new Student(person, 50, 150, false, null, null));
    }

    @Test
    @DisplayName("Should create minor Student when Responsible is provided")
    void shouldCreateMinorStudentWithResponsible() {
        LocalDate minorBirthday = LocalDate.now().minusYears(15);
        Person studentPerson = new Person("Minor Student", "minor@example.com", "11999999999", minorBirthday);

        LocalDate respBirthday = LocalDate.now().minusYears(40);
        Person respPerson = new Person("Parent", "parent@example.com", "11988888888", respBirthday);
        Responsible responsible = new Responsible(respPerson, "Father", null, null);

        Student student = new Student(studentPerson, 50, 150, false, null, responsible);

        assertNotNull(student);
        assertNotNull(student.getResponsible());
    }

    @Test
    @DisplayName("Should throw StudentException when weight or height is not positive")
    void shouldThrowExceptionWhenWeightOrHeightInvalid() {
        LocalDate adultBirthday = LocalDate.now().minusYears(20);
        Person person = new Person("Adult Student", "adult@example.com", "11999999999", adultBirthday);

        assertThrows(StudentException.class, () -> new Student(person, 0, 175, false, null, null));
        assertThrows(StudentException.class, () -> new Student(person, 75, -5, false, null, null));
    }

    @Test
    @DisplayName("Should throw StudentException when has health problem but description is empty")
    void shouldThrowExceptionWhenHealthProblemWithoutDescription() {
        LocalDate adultBirthday = LocalDate.now().minusYears(20);
        Person person = new Person("Adult Student", "adult@example.com", "11999999999", adultBirthday);

        assertThrows(StudentException.class, () -> new Student(person, 75, 175, true, "", null));
        assertThrows(StudentException.class, () -> new Student(person, 75, 175, true, null, null));
    }
}

