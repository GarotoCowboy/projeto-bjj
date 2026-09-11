package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateStudentRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.StudentResponse;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.UpdateStudentRequest;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.usecases.student.UpdateStudentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Alunos", description = "Endpoints para cadastro e gestão de alunos")
@RestController
@RequestMapping("/api/v1/bjj/students")
public class StudentController {

    private final SaveStudentUseCase saveStudentUseCase;
    private final FindAllStudentsUseCase findAllStudentsUseCase;
    private final FindAllActiveStudentsUseCase findAllActiveStudentsUseCase;
    private final FindStudentByIdUseCase findStudentByIdUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final ActiveStudentUseCase activeStudentUseCase;
    private final InactiveStudentUseCase inactiveStudentUseCase;
    private final StudentMapper studentMapper;

    public StudentController(
            SaveStudentUseCase saveStudentUseCase,
            FindAllStudentsUseCase findAllStudentsUseCase,
            FindAllActiveStudentsUseCase findAllActiveStudentsUseCase,
            FindStudentByIdUseCase findStudentByIdUseCase,
            UpdateStudentUseCase updateStudentUseCase,
            ActiveStudentUseCase activeStudentUseCase,
            InactiveStudentUseCase inactiveStudentUseCase,
            StudentMapper studentMapper) {
        this.saveStudentUseCase = saveStudentUseCase;
        this.findAllStudentsUseCase = findAllStudentsUseCase;
        this.findAllActiveStudentsUseCase = findAllActiveStudentsUseCase;
        this.findStudentByIdUseCase = findStudentByIdUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.activeStudentUseCase = activeStudentUseCase;
        this.inactiveStudentUseCase = inactiveStudentUseCase;
        this.studentMapper = studentMapper;
    }

    @Operation(summary = "Cadastrar um novo aluno", description = "Cria o cadastro de um aluno com suas informações pessoais e médicas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody @Valid CreateStudentRequest request) {
        // Construtor de negócio da Person (executa todas as validações)
        Person person = new Person(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.birthday()
        );

        // Se informou um ID de responsável válido (> 0), instancia o Responsible
        Responsible responsible = null;
        if (request.responsibleId() != null && request.responsibleId() > 0) {
            responsible = new Responsible(request.responsibleId(), null, null, null, null, null, null);
        }

        // Construtor de negócio do Student (executa validateResponsibility, validateWeight, validateHeight, etc.)
        Student student = new Student(
                person,
                request.weight(),
                request.height(),
                request.isHealthProblem(),
                request.healthProblemDescription(),
                responsible
        );

        Student saved = saveStudentUseCase.execute(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.fromDomain(saved));
    }

    @Operation(summary = "Listar alunos", description = "Retorna a lista de alunos cadastrados, com filtro opcional por status ativo.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<StudentResponse>> listAll(@RequestParam(required = false) Boolean active) {
        List<Student> students = Boolean.TRUE.equals(active)
                ? findAllActiveStudentsUseCase.execute()
                : findAllStudentsUseCase.execute();
        List<StudentResponse> list = students.stream()
                .map(studentMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Atualizar dados do aluno", description = "Atualiza peso, altura e condições de saúde do aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateStudentRequest request) {
        Student studentData = new Student();
        Person person = new Person();
        if (request.name() != null && !request.name().trim().isEmpty()) {
            person.setName(request.name().trim());
        }
        if (request.email() != null && !request.email().trim().isEmpty()) {
            person.setEmail(request.email().trim());
        }
        if (request.phoneNumber() != null && !request.phoneNumber().trim().isEmpty()) {
            person.setPhoneNumber(request.phoneNumber().trim());
        }
        if (request.birthday() != null) {
            person.setBirthday(request.birthday());
        }
        studentData.setPerson(person);
        studentData.setWeight(request.weight());
        studentData.setHeight(request.height());
        studentData.setIsHealthProblem(request.isHealthProblem());
        studentData.setHealthProblemDescription(request.healthProblemDescription());

        Student updated = updateStudentUseCase.execute(id, studentData);
        return ResponseEntity.ok(studentMapper.fromDomain(updated));
    }

    @Operation(summary = "Buscar aluno por ID", description = "Retorna os detalhes de um aluno específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {
        Student student = findStudentByIdUseCase.execute(id);
        return ResponseEntity.ok(studentMapper.fromDomain(student));
    }

    @Operation(summary = "Inativar aluno", description = "Altera o status do aluno para inativo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno inativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Aluno já inativo ou erro de negócio", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<StudentResponse> inactivate(@PathVariable Long id) {
        Student inactived = inactiveStudentUseCase.execute(id);
        return ResponseEntity.ok(studentMapper.fromDomain(inactived));
    }

    @Operation(summary = "Ativar aluno", description = "Altera o status do aluno para ativo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno ativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Aluno já ativo ou erro de negócio", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<StudentResponse> activate(@PathVariable Long id) {
        Student actived = activeStudentUseCase.execute(id);
        return ResponseEntity.ok(studentMapper.fromDomain(actived));
    }
}
