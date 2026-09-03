package br.com.pedro.sistema.gestao.pagamento.core.models;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.InstructorException;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InstructorTest {

    @Test
    @DisplayName("Should create instructor when valid adult belt and age >= 18")
    void shouldCreateInstructorWhenValid() {
        LocalDate adultBirthday = LocalDate.now().minusYears(28);
        Person person = new Person("Master Carlos", "carlos@example.com", "11988887777", adultBirthday);

        Instructor instructor = new Instructor(person, "carlos.bjj", "hashedPassword", Belt.BLACK);

        assertNotNull(instructor);
        assertEquals("carlos.bjj", instructor.getUsername());
        assertEquals(Belt.BLACK, instructor.getBelt());
    }

    @Test
    @DisplayName("Should throw InstructorException when instructor is under 18")
    void shouldThrowExceptionWhenInstructorIsMinor() {
        LocalDate minorBirthday = LocalDate.now().minusYears(17);
        Person person = new Person("Young Carlos", "carlos@example.com", "11988887777", minorBirthday);

        assertThrows(InstructorException.class, () -> new Instructor(person, "carlos.bjj", "hashedPassword", Belt.BLUE));
    }

    @Test
    @DisplayName("Should throw InstructorException when instructor has kids belt")
    void shouldThrowExceptionWhenInstructorHasKidsBelt() {
        LocalDate adultBirthday = LocalDate.now().minusYears(25);
        Person person = new Person("Carlos", "carlos@example.com", "11988887777", adultBirthday);

        assertThrows(InstructorException.class, () -> new Instructor(person, "carlos.bjj", "pass", Belt.YELLOW));
        assertThrows(InstructorException.class, () -> new Instructor(person, "carlos.bjj", "pass", Belt.WHITE_KIDS));
        assertThrows(InstructorException.class, () -> new Instructor(person, "carlos.bjj", "pass", Belt.GREEN));
    }
}

