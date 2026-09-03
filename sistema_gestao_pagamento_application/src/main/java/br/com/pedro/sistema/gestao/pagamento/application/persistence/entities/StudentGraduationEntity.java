package br.com.pedro.sistema.gestao.pagamento.application.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_student_graduations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGraduationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    @Column(name = "graduation_id", nullable = false)
    private Long graduationId;

    @Column(name = "graduation_day", nullable = false)
    private LocalDate graduationDay;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
