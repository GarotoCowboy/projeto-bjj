package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateStudentRequest(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Email is mandatory")
        @Email
        String email,
        @NotBlank(message = "Phone number is mandatory")
        String phoneNumber,
        @NotNull(message = "Birthday date mandatory")
        LocalDate birthday,
        @NotNull(message = "Weight is mandatory")
        @Positive(message = "Weight be is positive")
        int weight,
        @NotNull(message = "Height is mandatory")
        @Positive(message = "Weight be is positive")
        int height,
        @NotNull(message = "Health problem boolean is mandatory")
        boolean isHealthProblem,
        String healthProblemDescription,
        Long responsibleId
) {
}
