package br.com.pedro.sistema.gestao.pagamento.core.models;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.ContractTime;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MembershipTest {

    private Student student;

    @BeforeEach
    void setUp() {
        LocalDate birthday = LocalDate.now().minusYears(22);
        Person person = new Person("Lucas", "lucas@example.com", "11977777777", birthday);
        student = new Student(person, 80, 180, false, null, null);
    }

    @Test
    @DisplayName("Should create membership with PENDING status by default")
    void shouldCreateMembershipWithPendingStatus() {
        LocalDate dueDate = LocalDate.now().plusDays(10);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);

        assertEquals(PaymentStatus.PENDING, membership.getStatus());
        assertNull(membership.getPaymentDate());
        assertFalse(membership.isOverdue());
    }

    @Test
    @DisplayName("Should mark membership as paid successfully")
    void shouldMarkAsPaidSuccessfully() {
        LocalDate dueDate = LocalDate.now().plusDays(5);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);

        LocalDate paymentDate = LocalDate.now();
        membership.markAsPaid(paymentDate);

        assertEquals(PaymentStatus.PAID, membership.getStatus());
        assertEquals(paymentDate, membership.getPaymentDate());
        assertFalse(membership.isOverdue());
    }

    @Test
    @DisplayName("Should throw MembershipException when paying already paid membership")
    void shouldThrowExceptionWhenPayingAlreadyPaid() {
        LocalDate dueDate = LocalDate.now().plusDays(5);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);
        membership.markAsPaid(LocalDate.now());

        assertThrows(MembershipException.class, () -> membership.markAsPaid(LocalDate.now()));
    }

    @Test
    @DisplayName("Should cancel pending membership")
    void shouldCancelPendingMembership() {
        LocalDate dueDate = LocalDate.now().plusDays(5);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);

        membership.cancel();

        assertEquals(PaymentStatus.CANCELLED, membership.getStatus());
        assertFalse(membership.isOverdue());
    }

    @Test
    @DisplayName("Should throw MembershipException when cancelling paid membership")
    void shouldThrowExceptionWhenCancellingPaidMembership() {
        LocalDate dueDate = LocalDate.now().plusDays(5);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);
        membership.markAsPaid(LocalDate.now());

        assertThrows(MembershipException.class, membership::cancel);
    }

    @Test
    @DisplayName("Should correctly identify overdue membership")
    void shouldCorrectlyIdentifyOverdue() {
        LocalDate dueDate = LocalDate.now().minusDays(2);
        Membership membership = new Membership(student, new BigDecimal("150.00"), ContractTime.MONTHLY, dueDate);

        assertTrue(membership.isOverdue());
    }
}

