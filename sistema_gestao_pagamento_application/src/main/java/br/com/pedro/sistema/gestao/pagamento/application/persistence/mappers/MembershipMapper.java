package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.MembershipResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.MembershipEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

    public MembershipEntity toEntity(Membership membership) {
        if (membership == null) return null;

        return MembershipEntity.builder()
                .id(membership.getId())
                .studentId(membership.getStudent() != null ? membership.getStudent().getId() : null)
                .amount(membership.getAmount())
                .contractTime(membership.getContractTime())
                .status(membership.getStatus())
                .paymentDate(membership.getPaymentDate())
                .dueDate(membership.getDueDate())
                .createdAt(membership.getCreatedAt())
                .updatedAt(membership.getUpdatedAt())
                .build();
    }

    public Membership toDomain(MembershipEntity entity) {
        if (entity == null) return null;

        Student student = null;
        if (entity.getStudentId() != null) {
            student = new Student(entity.getStudentId(), null, true, 0, 0, false, null, null, null, null);
        }

        return toDomain(entity, student);
    }

    public Membership toDomain(MembershipEntity entity, Student student) {
        if (entity == null) return null;

        return new Membership(
                entity.getId(),
                student,
                entity.getAmount(),
                entity.getContractTime(),
                entity.getStatus(),
                entity.getPaymentDate(),
                entity.getDueDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public MembershipResponse fromDomain(Membership membership) {
        if (membership == null) return null;

        String studentName = (membership.getStudent() != null && membership.getStudent().getPerson() != null)
                ? membership.getStudent().getPerson().getName()
                : null;

        return new MembershipResponse(
                membership.getId(),
                membership.getStudent() != null ? membership.getStudent().getId() : null,
                studentName,
                membership.getAmount(),
                membership.getContractTime(),
                membership.getStatus(),
                membership.getPaymentDate(),
                membership.getDueDate(),
                membership.isOverdue(),
                membership.getCreatedAt(),
                membership.getUpdatedAt()
        );
    }
}
