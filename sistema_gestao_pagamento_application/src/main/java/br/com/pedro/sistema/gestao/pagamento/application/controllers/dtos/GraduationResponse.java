package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

import java.time.LocalDateTime;

public record GraduationResponse(
        Long id,
        Belt belt,
        int degree,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
