package br.com.pedro.sistema.gestao.pagamento.application.persistence.entities;

import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_graduations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"belt", "degree"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraduationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Belt belt;

    @Column(nullable = false)
    private int degree;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
