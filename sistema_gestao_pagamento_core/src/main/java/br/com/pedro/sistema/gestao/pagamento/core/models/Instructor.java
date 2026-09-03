package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDateTime;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.InstructorException;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;

public class Instructor {

    private Long id;
    private Person person;
    private String username;
    private String password;
    private Belt belt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    
    protected Instructor() {
    }


    public Instructor(Long id, Person person, String username, String password, Belt belt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.person = person;
        this.username = username;
        this.password = password;
        this.belt = belt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Instructor(Person person, String username, String password, Belt belt) {

        validateUsername(username);
        validatePassword(password);
        validateBelt(belt);
        validatePerson(person);
        validateInstructorBelt(belt);
        validateAge(person);

        this.username = username;
        this.person = person;
        this.password = password;
        this.belt = belt;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    private void validateAge(Person person) {
        if(person.getAge() < 18){
            throw new InstructorException(ErrorCode.IN_0002);
        }
    }

    private void validateInstructorBelt(Belt belt){
        if(belt == null){
            throw new InstructorException(ErrorCode.IN_0005);
        }

        if(belt == Belt.WHITE_KIDS || belt == Belt.GREY_WHITE || belt == Belt.GREY || belt == Belt.GREY_BLACK ||
           belt == Belt.YELLOW_WHITE || belt == Belt.YELLOW || belt == Belt.YELLOW_BLACK || belt == Belt.ORANGE_WHITE ||
           belt == Belt.ORANGE || belt == Belt.ORANGE_BLACK || belt == Belt.GREEN_WHITE || belt == Belt.GREEN ||
           belt == Belt.GREEN_BLACK){
            throw new InstructorException(ErrorCode.IN_0006);
        }
    }

    private void validateUsername(String username) {
        if(username == null || username.trim().isEmpty()){
            throw new InstructorException(ErrorCode.IN_0003);
        }
    }

    private void validatePassword(String password) {
        if(password == null || password.trim().isEmpty()){
            throw new InstructorException(ErrorCode.IN_0004);
        }
    }

    private void validateBelt(Belt belt) {
        if(belt == null){
            throw new InstructorException(ErrorCode.IN_0005);
        }
    }

    private void validatePerson(Person person) {
        if(person == null){
            throw new InstructorException(ErrorCode.IN_0001);
        }
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


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
        this.updatedAt = LocalDateTime.now();

    }


    public Belt getBelt() {
        return belt;
    }


    public void setBelt(Belt belt) {
        validateBelt(belt);
        this.belt = belt;
        this.updatedAt = LocalDateTime.now();

    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
        this.updatedAt = LocalDateTime.now();

    }

    

    @Override
    public String toString() {
        return "Instructor [id=" + id + ", person=" + person + ", username=" + username + ", password=" + password
                + ", belt=" + belt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        Instructor other = (Instructor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }




    
}
