package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateStudentGraduationRequest(
        @NotNull(message = "Student ID is mandatory")
        Long studentId,

        @NotNull(message = "Instructor ID is mandatory")
        Long instructorId,

        @NotNull(message = "Graduation ID is mandatory")
        Long graduationId,

        @NotNull(message = "Graduation day is mandatory")
        LocalDate graduationDay
) {
}
