package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.ResponsibleResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.ResponsibleEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResponsibleMapper {

    public ResponsibleEntity toEntity(Responsible responsible) {
        if (responsible == null) return null;
        Person person = responsible.getPerson();

        return ResponsibleEntity.builder()
                .id(responsible.getId())
                .name(person != null ? person.getName() : null)
                .email(person != null ? person.getEmail() : null)
                .phoneNumber(person != null ? person.getPhoneNumber() : null)
                .birthday(person != null ? person.getBirthday() : null)
                .relationship(responsible.getRelationship())
                .studentProfileId(responsible.getStudentProfile() != null ? responsible.getStudentProfile().getId() : null)
                .createdAt(responsible.getCreatedAt())
                .updatedAt(responsible.getUpdatedAt())
                .build();
    }

    public Responsible toDomain(ResponsibleEntity entity) {
        if (entity == null) return null;

        Person person = new Person(
                null,
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getBirthday(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

        Student studentProfile = null;
        if (entity.getStudentProfileId() != null) {
            studentProfile = new Student(entity.getStudentProfileId(), null, true, 0, 0, false, null, null, null, null);
        }

        return toDomain(entity, new ArrayList<>());
    }

    public Responsible toDomain(ResponsibleEntity entity, List<Student> dependents) {
        if (entity == null) return null;

        Person person = new Person(
                null,
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getBirthday(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

        Student studentProfile = null;
        if (entity.getStudentProfileId() != null) {
            studentProfile = new Student(entity.getStudentProfileId(), null, true, 0, 0, false, null, null, null, null);
        }

        return new Responsible(
                entity.getId(),
                person,
                entity.getRelationship(),
                studentProfile,
                dependents != null ? dependents : new ArrayList<>(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ResponsibleResponse fromDomain(Responsible responsible) {
        if (responsible == null) return null;
        Person person = responsible.getPerson();

        return new ResponsibleResponse(
                responsible.getId(),
                person != null ? person.getName() : null,
                person != null ? person.getEmail() : null,
                person != null ? person.getPhoneNumber() : null,
                person != null ? person.getBirthday() : null,
                responsible.getRelationship(),
                responsible.getStudentProfile() != null ? responsible.getStudentProfile().getId() : null,
                responsible.totalDependents(),
                responsible.getCreatedAt(),
                responsible.getUpdatedAt()
        );
    }
}
