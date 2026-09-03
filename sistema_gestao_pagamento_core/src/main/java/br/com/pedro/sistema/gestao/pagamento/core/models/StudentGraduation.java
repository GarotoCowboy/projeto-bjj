package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentGraduationException;

public class StudentGraduation {

    private Long id;
    private Student student;
    private Instructor instructor;
    private Graduation graduation;
    private LocalDateTime createdAt;
    private LocalDate graduationDay;

    public StudentGraduation() {
    }

    public StudentGraduation(Long id, Student student, Instructor instructor, Graduation graduation,
            LocalDateTime createdAt, LocalDate graduationDay) {
        this.id = id;
        this.student = student;
        this.instructor = instructor;
        this.graduation = graduation;
        this.createdAt = createdAt;
        this.graduationDay = graduationDay;
    }

    public StudentGraduation(Student student, Instructor instructor, Graduation graduation, LocalDate graduationDay) {

        validateStudent(student);
        validateInstructor(instructor);
        validateGraduation(graduation);
        validateGraduationDay(graduationDay);

        this.student = student;
        this.instructor = instructor;
        this.graduation = graduation;
        this.graduationDay = graduationDay;
        this.createdAt = LocalDateTime.now();
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new StudentGraduationException(ErrorCode.SG_0001);
        }
    }

    private void validateInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new StudentGraduationException(ErrorCode.SG_0002);
        }
    }

    private void validateGraduation(Graduation graduation) {
        if (graduation == null) {
            throw new StudentGraduationException(ErrorCode.SG_0003);
        }
    }

    private void validateGraduationDay(LocalDate graduationDay) {
        if (graduationDay == null) {
            throw new StudentGraduationException(ErrorCode.SG_0004);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Graduation getGraduation() {
        return graduation;
    }

    public void setGraduation(Graduation graduation) {
        this.graduation = graduation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getGraduationDay() {
        return graduationDay;
    }

    public void setGraduationDay(LocalDate graduationDay) {
        validateGraduationDay(graduationDay);
        this.graduationDay = graduationDay;
    }

    @Override
    public String toString() {
        return "StudentGraduation [id=" + id + ", student=" + student + ", instructor=" + instructor
                + ", graduation=" + graduation + ", graduationDay=" + graduationDay + ", createdAt=" + createdAt + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentGraduation other = (StudentGraduation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
