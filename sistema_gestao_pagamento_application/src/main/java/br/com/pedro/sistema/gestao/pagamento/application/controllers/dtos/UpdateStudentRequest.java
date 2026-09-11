package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateStudentRequest(
        String name,
        String email,
        String phoneNumber,
        LocalDate birthday,
        @NotNull(message = "Weight is mandatory")
        @Positive(message = "Weight must be positive")
        int weight,
        @NotNull(message = "Height is mandatory")
        @Positive(message = "Height must be positive")
        int height,
        @NotNull(message = "Health problem boolean is mandatory")
        boolean isHealthProblem,
        String healthProblemDescription
) {
}
