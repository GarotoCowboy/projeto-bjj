package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.GraduationResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.GraduationEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import org.springframework.stereotype.Component;

@Component
public class GraduationMapper {

    public GraduationEntity toEntity(Graduation graduation) {
        if (graduation == null) return null;

        return GraduationEntity.builder()
                .id(graduation.getId())
                .belt(graduation.getBelt())
                .degree(graduation.getDegree())
                .createdAt(graduation.getCreatedAt())
                .updatedAt(graduation.getUpdatedAt())
                .build();
    }

    public Graduation toDomain(GraduationEntity entity) {
        if (entity == null) return null;

        return new Graduation(
                entity.getId(),
                entity.getBelt(),
                entity.getDegree(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public GraduationResponse fromDomain(Graduation graduation) {
        if (graduation == null) return null;

        return new GraduationResponse(
                graduation.getId(),
                graduation.getBelt(),
                graduation.getDegree(),
                graduation.getCreatedAt(),
                graduation.getUpdatedAt()
        );
    }
}
