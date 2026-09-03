package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ResponsibleException;

public class Responsible {

    private Long id;
    private Person person;
    private String relationship;
    private Student studentProfile;
    private List<Student> dependents = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Responsible() {
    }

    public Responsible(Long id, Person person, String relationship, Student studentProfile, List<Student> dependents,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.person = person;
        this.relationship = relationship;
        this.studentProfile = studentProfile;
        this.dependents = dependents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Responsible(Person person, String relationship, Student studentProfile, List<Student> dependents) {

        validatePerson(person);
        validateRelationship(relationship);

        this.person = person;
        this.relationship = relationship;
        this.studentProfile = studentProfile;
        if (dependents != null) {
            this.dependents.addAll(dependents);
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

  
    private void validatePerson(Person person) {
        if (person == null) {
            throw new ResponsibleException(ErrorCode.RE_0001);
        }
    }

    private void validateRelationship(String relationship) {
        if (relationship == null || relationship.trim().isEmpty()) {
            throw new ResponsibleException(ErrorCode.RE_0002);
        }
    }

    public void addDependent(Student student) {
        if (student == null) {
            throw new ResponsibleException(ErrorCode.RE_0003);
        }

        if (!this.dependents.contains(student)) {
            dependents.add(student);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeDependent(Student student){
        if(student == null){
            throw new ResponsibleException(ErrorCode.RE_0003);
        }
        if(this.dependents.contains(student)){
            dependents.remove(student);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public boolean isStudent() {
        return this.studentProfile != null;
    }

    public int totalDependents() {
        return this.dependents.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        validatePerson(person);
        this.person = person;
        this.updatedAt = LocalDateTime.now();

    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        validateRelationship(relationship);
        this.relationship = relationship;
        this.updatedAt = LocalDateTime.now();

    }

    public Student getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(Student studentProfile) {
        this.studentProfile = studentProfile;
        this.updatedAt = LocalDateTime.now();

    }

    public List<Student> getDependents() {
        return Collections.unmodifiableList(dependents);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Responsible [id=" + id + ", person=" + person + ", relationship=" + relationship + ", studentProfile="
                + studentProfile + ", dependents=" + dependents + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + "]";
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
        Responsible other = (Responsible) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
