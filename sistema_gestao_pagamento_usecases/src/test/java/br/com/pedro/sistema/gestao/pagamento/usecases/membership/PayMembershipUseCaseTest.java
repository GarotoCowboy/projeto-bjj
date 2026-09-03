package br.com.pedro.sistema.gestao.pagamento.usecases.membership;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.MembershipException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.ContractTime;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayMembershipUseCaseTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private PayMembershipUseCase payMembershipUseCase;

    @Test
    @DisplayName("Should mark membership as paid successfully")
    void shouldPayMembershipSuccessfully() {
        LocalDate birthday = LocalDate.now().minusYears(20);
        Person person = new Person("Lucas", "lucas@example.com", "11999999999", birthday);
        Student student = new Student(person, 70, 170, false, null, null);
        Membership membership = new Membership(student, new BigDecimal("100.00"), ContractTime.MONTHLY, LocalDate.now().plusDays(5));

        when(membershipRepository.findById(1L)).thenReturn(Optional.of(membership));
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Membership paid = payMembershipUseCase.execute(1L, LocalDate.now());

        assertNotNull(paid);
        assertEquals(PaymentStatus.PAID, paid.getStatus());
        assertNotNull(paid.getPaymentDate());
        verify(membershipRepository, times(1)).save(membership);
    }

    @Test
    @DisplayName("Should throw MembershipException when membership not found")
    void shouldThrowExceptionWhenNotFound() {
        when(membershipRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(MembershipException.class, () -> payMembershipUseCase.execute(99L, LocalDate.now()));
        verify(membershipRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw MembershipException when ID is null")
    void shouldThrowExceptionWhenIdNull() {
        assertThrows(MembershipException.class, () -> payMembershipUseCase.execute(null, LocalDate.now()));
    }
}

