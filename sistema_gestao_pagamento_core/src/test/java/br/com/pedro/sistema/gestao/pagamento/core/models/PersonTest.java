package br.com.pedro.sistema.gestao.pagamento.core.models;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.PersonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    @DisplayName("Should create Person when valid data is provided")
    void shouldCreatePersonWhenValidData() {
        LocalDate birthday = LocalDate.now().minusYears(25);
        Person person = new Person("Pedro Silva", "pedro@example.com", "11999999999", birthday);

        assertEquals("Pedro Silva", person.getName());
        assertEquals("pedro@example.com", person.getEmail());
        assertEquals("11999999999", person.getPhoneNumber());
        assertEquals(birthday, person.getBirthday());
        assertEquals(25, person.getAge());
    }

    @Test
    @DisplayName("Should throw PersonException when name is null or empty")
    void shouldThrowExceptionWhenNameIsInvalid() {
        LocalDate birthday = LocalDate.now().minusYears(20);
        assertThrows(PersonException.class, () -> new Person(null, "pedro@example.com", "11999999999", birthday));
        assertThrows(PersonException.class, () -> new Person("   ", "pedro@example.com", "11999999999", birthday));
    }

    @Test
    @DisplayName("Should throw PersonException when email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        LocalDate birthday = LocalDate.now().minusYears(20);
        assertThrows(PersonException.class, () -> new Person("Pedro", "invalid-email", "11999999999", birthday));
        assertThrows(PersonException.class, () -> new Person("Pedro", null, "11999999999", birthday));
    }

    @Test
    @DisplayName("Should throw PersonException when birthday is null")
    void shouldThrowExceptionWhenBirthdayIsNull() {
        assertThrows(PersonException.class, () -> new Person("Pedro", "pedro@example.com", "11999999999", null));
    }
}

