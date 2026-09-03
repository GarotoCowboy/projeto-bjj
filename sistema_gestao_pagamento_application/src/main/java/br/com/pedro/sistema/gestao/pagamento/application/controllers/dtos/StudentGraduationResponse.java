package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentGraduationResponse(
        Long id,
        Long studentId,
        String studentName,
        Long instructorId,
        String instructorName,
        Long graduationId,
        Belt belt,
        int degree,
        LocalDate graduationDay,
        LocalDateTime createdAt
) {
}
