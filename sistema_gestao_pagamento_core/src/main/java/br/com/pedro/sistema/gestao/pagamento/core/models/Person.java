package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.PersonException;

public class Person {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Person(Long id, String name, String email, String phoneNumber, LocalDate birthday, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected Person() {
    }

    public Person(String name, String email,String phoneNumber, LocalDate birthday) {

              validateName(name);
            validateEmail(email);
            validatePhoneNumber(phoneNumber);
            validateAge(birthday);

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new PersonException(ErrorCode.PE_0001);
        }

    }

    private void validateAge(LocalDate birthday) {
        if(birthday == null){
            throw new PersonException(ErrorCode.PE_0004);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.trim().isEmpty()){
             throw new PersonException(ErrorCode.PE_0003);
        }
        
    }

    private void validateEmail(String email) {
        if(email == null || email.trim().isEmpty() || !email.contains("@")){
            throw new PersonException(ErrorCode.PE_0002);
        }
    }

    public int getAge() {
        if(this.birthday == null){
            throw new PersonException(ErrorCode.PE_0004);
        }
        return Period.between(this.birthday, LocalDate.now()).getYears();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        validateAge(birthday);
        this.birthday = birthday;
        this.updatedAt = LocalDateTime.now();

    }

   
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
                + ", birthday=" + birthday + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        Person other = (Person) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }


}
