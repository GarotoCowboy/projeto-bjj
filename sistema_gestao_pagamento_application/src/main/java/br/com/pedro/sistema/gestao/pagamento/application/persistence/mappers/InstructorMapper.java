package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.InstructorResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.InstructorEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public InstructorEntity toEntity(Instructor instructor) {
        if (instructor == null) return null;
        Person person = instructor.getPerson();

        return InstructorEntity.builder()
                .id(instructor.getId())
                .name(person != null ? person.getName() : null)
                .email(person != null ? person.getEmail() : null)
                .phoneNumber(person != null ? person.getPhoneNumber() : null)
                .birthday(person != null ? person.getBirthday() : null)
                .username(instructor.getUsername())
                .password(instructor.getPassword())
                .belt(instructor.getBelt())
                .createdAt(instructor.getCreatedAt())
                .updatedAt(instructor.getUpdatedAt())
                .build();
    }

    public Instructor toDomain(InstructorEntity entity) {
        if (entity == null) return null;

        Person person = new Person(
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getBirthday()
        );

        return new Instructor(
                entity.getId(),
                person,
                entity.getUsername(),
                entity.getPassword(),
                entity.getBelt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public InstructorResponse fromDomain(Instructor instructor) {
        if (instructor == null) return null;
        Person person = instructor.getPerson();

        return new InstructorResponse(
                instructor.getId(),
                person != null ? person.getName() : null,
                person != null ? person.getEmail() : null,
                person != null ? person.getPhoneNumber() : null,
                person != null ? person.getBirthday() : null,
                instructor.getUsername(),
                instructor.getBelt(),
                instructor.getCreatedAt(),
                instructor.getUpdatedAt()
        );
    }
}
