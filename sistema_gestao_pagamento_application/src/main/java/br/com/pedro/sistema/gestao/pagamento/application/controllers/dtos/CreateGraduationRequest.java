package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateGraduationRequest(
        @NotNull(message = "Belt is mandatory")
        Belt belt,

        @Min(value = 0, message = "Degree must be at least 0")
        @Max(value = 4, message = "Degree cannot exceed 4")
        int degree
) {
}
