package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDate birthday,
        boolean isActive,
        int weight,
        int height,
        boolean isHealthProblem,
        String healthProblemDescription,
        Long responsibleId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
