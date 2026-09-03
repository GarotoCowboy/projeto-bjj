package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateMembershipRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.MembershipResponse;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.MembershipMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import br.com.pedro.sistema.gestao.pagamento.usecases.membership.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Mensalidades", description = "Endpoints para controle de cobranças, planos e pagamentos")
@RestController
@RequestMapping("/api/v1/bjj/memberships")
public class MembershipController {

    private final SaveMembershipUseCase saveMembershipUseCase;
    private final FindAllMembershipsUseCase findAllMembershipsUseCase;
    private final FindMembershipByIdUseCase findMembershipByIdUseCase;
    private final FindMembershipByStatusUseCase findMembershipByStatusUseCase;
    private final FindMembershipByStudentIdUseCase findMembershipByStudentIdUseCase;
    private final PayMembershipUseCase payMembershipUseCase;
    private final CancelMembershipUseCase cancelMembershipUseCase;
    private final MembershipMapper membershipMapper;

    public MembershipController(
            SaveMembershipUseCase saveMembershipUseCase,
            FindAllMembershipsUseCase findAllMembershipsUseCase,
            FindMembershipByIdUseCase findMembershipByIdUseCase,
            FindMembershipByStatusUseCase findMembershipByStatusUseCase,
            FindMembershipByStudentIdUseCase findMembershipByStudentIdUseCase,
            PayMembershipUseCase payMembershipUseCase,
            CancelMembershipUseCase cancelMembershipUseCase,
            MembershipMapper membershipMapper) {
        this.saveMembershipUseCase = saveMembershipUseCase;
        this.findAllMembershipsUseCase = findAllMembershipsUseCase;
        this.findMembershipByIdUseCase = findMembershipByIdUseCase;
        this.findMembershipByStatusUseCase = findMembershipByStatusUseCase;
        this.findMembershipByStudentIdUseCase = findMembershipByStudentIdUseCase;
        this.payMembershipUseCase = payMembershipUseCase;
        this.cancelMembershipUseCase = cancelMembershipUseCase;
        this.membershipMapper = membershipMapper;
    }

    @Operation(summary = "Criar uma nova mensalidade/cobrança", description = "Gera uma nova mensalidade vinculada a um aluno com valor, plano e vencimento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mensalidade gerada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<MembershipResponse> create(@RequestBody @Valid CreateMembershipRequest request) {
        Student student = new Student(request.studentId(), null, true, 0, 0, false, null, null, null, null);
        Membership membership = new Membership(
                student,
                request.amount(),
                request.contractTime(),
                request.dueDate()
        );

        Membership saved = saveMembershipUseCase.execute(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipMapper.fromDomain(saved));
    }

    @Operation(summary = "Listar todas as mensalidades", description = "Retorna todas as mensalidades registradas.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<MembershipResponse>> listAll() {
        List<MembershipResponse> list = findAllMembershipsUseCase.execute().stream()
                .map(membershipMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar mensalidade por ID", description = "Retorna os detalhes de uma mensalidade pelo seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensalidade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mensalidade não encontrada", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponse> findById(@PathVariable Long id) {
        Membership membership = findMembershipByIdUseCase.execute(id);
        return ResponseEntity.ok(membershipMapper.fromDomain(membership));
    }

    @Operation(summary = "Listar mensalidades por aluno", description = "Retorna todas as mensalidades cadastradas para um aluno específico.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MembershipResponse>> findByStudentId(@PathVariable Long studentId) {
        List<MembershipResponse> list = findMembershipByStudentIdUseCase.execute(studentId).stream()
                .map(membershipMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Listar mensalidades por status", description = "Filtra as mensalidades pelo status (PENDING, PAID, CANCELLED).")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MembershipResponse>> findByStatus(@PathVariable PaymentStatus status) {
        List<MembershipResponse> list = findMembershipByStatusUseCase.execute(status).stream()
                .map(membershipMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Pagar mensalidade", description = "Dá baixa em uma mensalidade pendente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensalidade paga com sucesso"),
            @ApiResponse(responseCode = "400", description = "Mensalidade já paga ou erro de negócio", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Mensalidade não encontrada", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PatchMapping("/{id}/pay")
    public ResponseEntity<MembershipResponse> pay(
            @PathVariable Long id,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate paymentDate) {
        Membership paid = payMembershipUseCase.execute(id, paymentDate);
        return ResponseEntity.ok(membershipMapper.fromDomain(paid));
    }

    @Operation(summary = "Cancelar mensalidade", description = "Cancela uma mensalidade pendente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensalidade cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Mensalidade já paga ou erro de negócio", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Mensalidade não encontrada", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<MembershipResponse> cancel(@PathVariable Long id) {
        Membership cancelled = cancelMembershipUseCase.execute(id);
        return ResponseEntity.ok(membershipMapper.fromDomain(cancelled));
    }
}
