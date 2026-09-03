package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResponsibleResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDate birthday,
        String relationship,
        Long studentProfileId,
        int totalDependents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
