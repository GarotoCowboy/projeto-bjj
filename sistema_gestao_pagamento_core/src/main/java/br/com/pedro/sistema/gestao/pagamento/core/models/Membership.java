package br.com.pedro.sistema.gestao.pagamento.core.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.ContractTime;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;

public class Membership {

    private Long id;
    private Student student;
    private BigDecimal amount;
    private ContractTime contractTime;
    private PaymentStatus status;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Membership() {
    }

    public Membership(Long id, Student student, BigDecimal amount, ContractTime contractTime, PaymentStatus status,
            LocalDate paymentDate, LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.student = student;
        this.amount = amount;
        this.contractTime = contractTime;
        this.status = status;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Membership(Student student, BigDecimal amount, ContractTime contractTime, LocalDate dueDate) {

        validateStudent(student);
        validateAmount(amount);
        validateContractTime(contractTime);
        validateDueDate(dueDate);

        this.student = student;
        this.amount = amount;
        this.contractTime = contractTime;
        this.status = PaymentStatus.PENDING;
        this.paymentDate = null;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue(LocalDate referenceDate) {
        if (this.status == PaymentStatus.PAID || this.status == PaymentStatus.CANCELLED) {
            return false;
        }
        if (referenceDate == null) {
            throw new MembershipException(ErrorCode.MB_0006);
        }
        return referenceDate.isAfter(this.dueDate);
    }

    public boolean isOverdue() {
        return isOverdue(LocalDate.now());
    }

    public void markAsPaid(LocalDate paymentDate) {
        if (this.status == PaymentStatus.PAID) {
            throw new MembershipException(ErrorCode.MB_0008);
        }
        if (paymentDate == null) {
            throw new MembershipException(ErrorCode.MB_0007);
        }
        this.paymentDate = paymentDate;
        this.status = PaymentStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == PaymentStatus.PAID) {
            throw new MembershipException(ErrorCode.MB_0009);
        }
        this.status = PaymentStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new MembershipException(ErrorCode.MB_0001);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new MembershipException(ErrorCode.MB_0002);
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MembershipException(ErrorCode.MB_0003);
        }
    }

    private void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new MembershipException(ErrorCode.MB_0005);
        }
    }

    private void validateContractTime(ContractTime contractTime) {
        if (contractTime == null) {
            throw new MembershipException(ErrorCode.MB_0004);
        }
    }

    // private void validatePaymentStatus(PaymentStatus paymentStatus){
    // if(paymentStatus == null){
    // throw new IllegalArgumentException("Payment Status Cannot be Null");
    // }
    // }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ContractTime getContractTime() {
        return contractTime;
    }

    public void setContractTime(ContractTime contractTime) {
        this.contractTime = contractTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Membership [id=" + id + ", student=" + student + ", amount=" + amount + ", contractTime=" + contractTime
                + ", status=" + status + ", paymentDate=" + paymentDate + ", dueDate=" + dueDate + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
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
        Membership other = (Membership) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
