package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateInstructorRequest(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        String phoneNumber,

        @NotNull(message = "Birthday is mandatory")
        LocalDate birthday,

        @NotBlank(message = "Username is mandatory")
        String username,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotNull(message = "Belt is mandatory")
        Belt belt
) {
}
