package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDateTime;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.GraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

public class Graduation {

    private Long id;
    private Belt belt;
    private int degree;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Graduation() {
    }
    
    public Graduation(Long id, Belt belt, int degree, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.belt = belt;
        this.degree = degree;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Graduation(Belt belt, int degree) {
        validateBelt(belt);
        validateDegree(degree);

        this.belt = belt;
        this.degree = degree;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateDegree(int degree) {
        if (degree < 0 || degree > 4) {
            throw new GraduationException(ErrorCode.GR_0002);
        }
    }

    private void validateBelt(Belt belt) {
        if (belt == null) {
            throw new GraduationException(ErrorCode.GR_0001);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Belt getBelt() {
        return belt;
    }

    public void setBelt(Belt belt) {
        validateBelt(belt);
        this.belt = belt;
        this.updatedAt = LocalDateTime.now();
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        validateDegree(degree);
        this.degree = degree;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Graduation [id=" + id + ", belt=" + belt + ", degree=" + degree + "]";
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
        Graduation other = (Graduation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

