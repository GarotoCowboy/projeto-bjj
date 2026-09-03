package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.ContractTime;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateMembershipRequest(
        @NotNull(message = "Student ID is mandatory")
        Long studentId,

        @NotNull(message = "Amount is mandatory")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Contract time is mandatory")
        ContractTime contractTime,

        @NotNull(message = "Due date is mandatory")
        LocalDate dueDate
) {
}
