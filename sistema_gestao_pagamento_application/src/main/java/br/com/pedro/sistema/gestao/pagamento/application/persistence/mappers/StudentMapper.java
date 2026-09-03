package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.StudentResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentEntity toEntity(Student student) {
        if (student == null) return null;
        Person person = student.getPerson();

        Long responsibleId = (student.getResponsible() != null && student.getResponsible().getId() != null && student.getResponsible().getId() > 0)
                ? student.getResponsible().getId()
                : null;

        return StudentEntity.builder()
                .id(student.getId())
                .name(person != null ? person.getName() : null)
                .email(person != null ? person.getEmail() : null)
                .phoneNumber(person != null ? person.getPhoneNumber() : null)
                .birthday(person != null ? person.getBirthday() : null)
                .weight(student.getWeight())
                .height(student.getHeight())
                .isActive(student.getIsActive())
                .isHealthProblem(student.getIsHealthProblem())
                .healthProblemDescription(student.getHealthProblemDescription())
                .responsibleId(responsibleId)
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }

    public Student toDomain(StudentEntity studentEntity) {
        if (studentEntity == null) return null;

        Person person = new Person(
                null,
                studentEntity.getName(),
                studentEntity.getEmail(),
                studentEntity.getPhoneNumber(),
                studentEntity.getBirthday(),
                studentEntity.getCreatedAt(),
                studentEntity.getUpdatedAt()
        );

        Responsible responsible = null;
        if (studentEntity.getResponsibleId() != null && studentEntity.getResponsibleId() > 0) {
            responsible = new Responsible(studentEntity.getResponsibleId(), null, null, null, null, null, null);
        }

        return new Student(
                studentEntity.getId(),
                person,
                studentEntity.isActive(),
                studentEntity.getWeight(),
                studentEntity.getHeight(),
                studentEntity.isHealthProblem(),
                studentEntity.getHealthProblemDescription(),
                responsible,
                studentEntity.getCreatedAt(),
                studentEntity.getUpdatedAt()
        );
    }

    public StudentResponse fromDomain(Student student) {
        if (student == null) return null;
        Person person = student.getPerson();

        Long responsibleId = (student.getResponsible() != null && student.getResponsible().getId() != null && student.getResponsible().getId() > 0)
                ? student.getResponsible().getId()
                : null;

        return new StudentResponse(
                student.getId(),
                person != null ? person.getName() : null,
                person != null ? person.getEmail() : null,
                person != null ? person.getPhoneNumber() : null,
                person != null ? person.getBirthday() : null,
                student.getIsActive(),
                student.getWeight(),
                student.getHeight(),
                student.getIsHealthProblem(),
                student.getHealthProblemDescription(),
                responsibleId,
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
