package br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.StudentGraduationResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentGraduationEntity;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import org.springframework.stereotype.Component;

@Component
public class StudentGraduationMapper {

    public StudentGraduationEntity toEntity(StudentGraduation studentGraduation) {
        if (studentGraduation == null) return null;

        return StudentGraduationEntity.builder()
                .id(studentGraduation.getId())
                .studentId(studentGraduation.getStudent() != null ? studentGraduation.getStudent().getId() : null)
                .instructorId(studentGraduation.getInstructor() != null ? studentGraduation.getInstructor().getId() : null)
                .graduationId(studentGraduation.getGraduation() != null ? studentGraduation.getGraduation().getId() : null)
                .graduationDay(studentGraduation.getGraduationDay())
                .createdAt(studentGraduation.getCreatedAt())
                .build();
    }

    public StudentGraduation toDomain(StudentGraduationEntity entity) {
        if (entity == null) return null;

        Student student = entity.getStudentId() != null
                ? new Student(entity.getStudentId(), null, true, 0, 0, false, null, null, null, null)
                : null;

        Instructor instructor = entity.getInstructorId() != null
                ? new Instructor(entity.getInstructorId(), null, null, null, null, null, null)
                : null;

        Graduation graduation = entity.getGraduationId() != null
                ? new Graduation(entity.getGraduationId(), null, 0, null, null)
                : null;

        return toDomain(entity, student, instructor, graduation);
    }

    public StudentGraduation toDomain(
            StudentGraduationEntity entity,
            Student student,
            Instructor instructor,
            Graduation graduation) {
        if (entity == null) return null;

        return new StudentGraduation(
                entity.getId(),
                student,
                instructor,
                graduation,
                entity.getCreatedAt(),
                entity.getGraduationDay()
        );
    }

    public StudentGraduationResponse fromDomain(StudentGraduation studentGraduation) {
        if (studentGraduation == null) return null;

        String studentName = (studentGraduation.getStudent() != null && studentGraduation.getStudent().getPerson() != null)
                ? studentGraduation.getStudent().getPerson().getName()
                : null;

        String instructorName = (studentGraduation.getInstructor() != null && studentGraduation.getInstructor().getPerson() != null)
                ? studentGraduation.getInstructor().getPerson().getName()
                : null;

        return new StudentGraduationResponse(
                studentGraduation.getId(),
                studentGraduation.getStudent() != null ? studentGraduation.getStudent().getId() : null,
                studentName,
                studentGraduation.getInstructor() != null ? studentGraduation.getInstructor().getId() : null,
                instructorName,
                studentGraduation.getGraduation() != null ? studentGraduation.getGraduation().getId() : null,
                studentGraduation.getGraduation() != null ? studentGraduation.getGraduation().getBelt() : null,
                studentGraduation.getGraduation() != null ? studentGraduation.getGraduation().getDegree() : 0,
                studentGraduation.getGraduationDay(),
                studentGraduation.getCreatedAt()
        );
    }
}
