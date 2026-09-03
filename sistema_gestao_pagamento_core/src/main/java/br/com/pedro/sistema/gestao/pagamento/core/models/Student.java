package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDateTime;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;

public class Student {

    private Long id;
    private Person person;
    private boolean isActive;
    private int  weight;
    private int height;

    private boolean isHealthProblem;
    private String healthProblemDescription;

    private Responsible responsible;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Student() {
    }

    
    public Student(Long id, Person person, boolean isActive, int weight, int height, boolean isHealthProblem,
            String healthProblemDescription, Responsible responsible, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.person = person;
        this.isActive = isActive;
        this.weight = weight;
        this.height = height;
        this.isHealthProblem = isHealthProblem;
        this.healthProblemDescription = healthProblemDescription;
        this.responsible = responsible;
                this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Student(Person person, int weight, int height, boolean isHealthProblem,
            String healthProblemDescription, Responsible responsible) {


        validatePerson(person);
        validateWeight(weight);
        validateHeight(height);
        validateHealthProblemDescription(healthProblemDescription, isHealthProblem);
        validateResponsibility(person, responsible);

        
        this.person = person;
        this.isActive = true;
        this.weight = weight;
        this.height = height;
        this.isHealthProblem = isHealthProblem;
        this.healthProblemDescription = healthProblemDescription;
        this.responsible = responsible;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validatePerson(Person person) {
        if(person == null){
            throw new StudentException(ErrorCode.ST_0001);
        }
    }

    private void validateResponsibility(Person person,Responsible responsible) {
        if(person.getAge() < 18 && responsible == null){
            throw new StudentException(ErrorCode.ST_0002);
            
        }
    }

    private void validateWeight(int weight) {
        if(weight <= 0){
            throw new StudentException(ErrorCode.ST_0003);
        }
    }

    private void validateHeight(int height) {
     
        if(height <= 0){
            throw new StudentException(ErrorCode.ST_0004);
        }
    }

    private void validateHealthProblemDescription(String healthProblemDescription, boolean isHealthProblem) {
        if(isHealthProblem && (healthProblemDescription == null || healthProblemDescription.trim().isEmpty())){
            throw new StudentException(ErrorCode.ST_0005);
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
        this.updatedAt= LocalDateTime.now();
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        updatedAt = LocalDateTime.now();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        validateWeight(weight);
        this.weight = weight;
        this.updatedAt=LocalDateTime.now();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        validateHeight(height);
        this.height = height;
        this.updatedAt=LocalDateTime.now();
    }

    public boolean getIsHealthProblem() {
        return isHealthProblem;
    }

    public void setIsHealthProblem(boolean isHealthProblem) {
        this.isHealthProblem = isHealthProblem;
        this.updatedAt = LocalDateTime.now();
    }

    public String getHealthProblemDescription() {
        return healthProblemDescription;
    }

    public void setHealthProblemDescription(String healthProblemDescription) {
        validateHealthProblemDescription(healthProblemDescription, getIsHealthProblem());
        this.healthProblemDescription = healthProblemDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
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
        return "Student [id=" + id + ", person=" + person + ", isActive=" + isActive + ", weight=" + weight
                + ", height=" + height + ", isHealthProblem=" + isHealthProblem + ", healthProblemDescription="
                + healthProblemDescription + ", responsible=" + responsible + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
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
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }




    
}
