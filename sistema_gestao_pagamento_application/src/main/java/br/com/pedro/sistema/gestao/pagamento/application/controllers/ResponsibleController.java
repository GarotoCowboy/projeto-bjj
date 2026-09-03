package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateResponsibleRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.ResponsibleResponse;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.ResponsibleMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.FindAllResponsiblesUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.FindResponsibleByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.responsible.SaveResponsibleUseCase;
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

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Responsáveis", description = "Endpoints para cadastro e gestão de responsáveis por alunos menores")
@RestController
@RequestMapping("/api/v1/bjj/responsibles")
public class ResponsibleController {

    private final SaveResponsibleUseCase saveResponsibleUseCase;
    private final FindAllResponsiblesUseCase findAllResponsiblesUseCase;
    private final FindResponsibleByIdUseCase findResponsibleByIdUseCase;
    private final ResponsibleMapper responsibleMapper;

    public ResponsibleController(
            SaveResponsibleUseCase saveResponsibleUseCase,
            FindAllResponsiblesUseCase findAllResponsiblesUseCase,
            FindResponsibleByIdUseCase findResponsibleByIdUseCase,
            ResponsibleMapper responsibleMapper) {
        this.saveResponsibleUseCase = saveResponsibleUseCase;
        this.findAllResponsiblesUseCase = findAllResponsiblesUseCase;
        this.findResponsibleByIdUseCase = findResponsibleByIdUseCase;
        this.responsibleMapper = responsibleMapper;
    }

    @Operation(summary = "Cadastrar um novo responsável", description = "Cadastra um responsável financeiro/legal para alunos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Responsável cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<ResponsibleResponse> create(@RequestBody @Valid CreateResponsibleRequest request) {
        // Construtor de negócio de Person
        Person person = new Person(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.birthday()
        );

        Student studentProfile = null;
        if (request.studentProfileId() != null) {
            studentProfile = new Student(request.studentProfileId(), null, true, 0, 0, false, null, null, null, null);
        }

        // Construtor de negócio de Responsible (valida Person e Relationship)
        Responsible responsible = new Responsible(
                person,
                request.relationship(),
                studentProfile,
                new ArrayList<>()
        );

        Responsible saved = saveResponsibleUseCase.execute(responsible);
        return ResponseEntity.status(HttpStatus.CREATED).body(responsibleMapper.fromDomain(saved));
    }

    @Operation(summary = "Listar todos os responsáveis", description = "Retorna todos os responsáveis cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ResponsibleResponse>> listAll() {
        List<ResponsibleResponse> list = findAllResponsiblesUseCase.execute().stream()
                .map(responsibleMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar responsável por ID", description = "Retorna os detalhes de um responsável pelo seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsável encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponsibleResponse> findById(@PathVariable Long id) {
        Responsible responsible = findResponsibleByIdUseCase.execute(id);
        return ResponseEntity.ok(responsibleMapper.fromDomain(responsible));
    }
}
