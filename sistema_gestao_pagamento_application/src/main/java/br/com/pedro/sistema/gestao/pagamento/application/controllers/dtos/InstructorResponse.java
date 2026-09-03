package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InstructorResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDate birthday,
        String username,
        Belt belt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
