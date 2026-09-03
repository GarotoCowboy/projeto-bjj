package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.ContractTime;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MembershipResponse(
        Long id,
        Long studentId,
        String studentName,
        BigDecimal amount,
        ContractTime contractTime,
        PaymentStatus status,
        LocalDate paymentDate,
        LocalDate dueDate,
        boolean isOverdue,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
