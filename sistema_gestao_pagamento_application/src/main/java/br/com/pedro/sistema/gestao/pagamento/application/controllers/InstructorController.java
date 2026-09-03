package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateInstructorRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.InstructorResponse;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.InstructorMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindAllInstructorsUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindInstructorByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindInstructorByUsernameUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.SaveInstructorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Instrutores", description = "Endpoints para cadastro e gestão de instrutores")
@RestController
@RequestMapping("/api/v1/bjj/instructors")
public class InstructorController {

    private final SaveInstructorUseCase saveInstructorUseCase;
    private final FindAllInstructorsUseCase findAllInstructorsUseCase;
    private final FindInstructorByIdUseCase findInstructorByIdUseCase;
    private final FindInstructorByUsernameUseCase findInstructorByUsernameUseCase;
    private final InstructorMapper instructorMapper;
    private final PasswordEncoder passwordEncoder;

    public InstructorController(
            SaveInstructorUseCase saveInstructorUseCase,
            FindAllInstructorsUseCase findAllInstructorsUseCase,
            FindInstructorByIdUseCase findInstructorByIdUseCase,
            FindInstructorByUsernameUseCase findInstructorByUsernameUseCase,
            InstructorMapper instructorMapper,
            PasswordEncoder passwordEncoder) {
        this.saveInstructorUseCase = saveInstructorUseCase;
        this.findAllInstructorsUseCase = findAllInstructorsUseCase;
        this.findInstructorByIdUseCase = findInstructorByIdUseCase;
        this.findInstructorByUsernameUseCase = findInstructorByUsernameUseCase;
        this.instructorMapper = instructorMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Cadastrar um novo instrutor", description = "Cadastra um instrutor com sua graduação (faixa) e credenciais de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instrutor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<InstructorResponse> create(@RequestBody @Valid CreateInstructorRequest request) {
        // Construtor de negócio de Person
        Person person = new Person(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.birthday()
        );

        String hashedPassword = passwordEncoder.encode(request.password());
        // Construtor de negócio de Instructor (valida idade >= 18, faixa não infantil, etc.)
        Instructor instructor = new Instructor(
                person,
                request.username(),
                hashedPassword,
                request.belt()
        );

        Instructor saved = saveInstructorUseCase.execute(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorMapper.fromDomain(saved));
    }

    @Operation(summary = "Listar todos os instrutores", description = "Retorna todos os instrutores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<InstructorResponse>> listAll() {
        List<InstructorResponse> list = findAllInstructorsUseCase.execute().stream()
                .map(instructorMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar instrutor por ID", description = "Retorna os detalhes de um instrutor pelo seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrutor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> findById(@PathVariable Long id) {
        Instructor instructor = findInstructorByIdUseCase.execute(id);
        return ResponseEntity.ok(instructorMapper.fromDomain(instructor));
    }

    @Operation(summary = "Buscar instrutor por username", description = "Retorna os detalhes de um instrutor pelo seu nome de usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrutor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<InstructorResponse> findByUsername(@PathVariable String username) {
        Instructor instructor = findInstructorByUsernameUseCase.execute(username);
        return ResponseEntity.ok(instructorMapper.fromDomain(instructor));
    }
}
