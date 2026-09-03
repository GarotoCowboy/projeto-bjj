package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateStudentGraduationRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.StudentGraduationResponse;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentGraduationMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindByInstructorIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindByStudentIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.FindStudentGraduationByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation.SaveStudentGraduationUseCase;
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

@Tag(name = "Graduações de Alunos", description = "Endpoints para registro e histórico de graduações (faixas e graus) dos alunos")
@RestController
@RequestMapping("/api/v1/bjj/student-graduations")
public class StudentGraduationController {

    private final SaveStudentGraduationUseCase saveStudentGraduationUseCase;
    private final FindStudentGraduationByIdUseCase findStudentGraduationByIdUseCase;
    private final FindByStudentIdUseCase findByStudentIdUseCase;
    private final FindByInstructorIdUseCase findByInstructorIdUseCase;
    private final StudentGraduationMapper studentGraduationMapper;

    public StudentGraduationController(
            SaveStudentGraduationUseCase saveStudentGraduationUseCase,
            FindStudentGraduationByIdUseCase findStudentGraduationByIdUseCase,
            FindByStudentIdUseCase findByStudentIdUseCase,
            FindByInstructorIdUseCase findByInstructorIdUseCase,
            StudentGraduationMapper studentGraduationMapper) {
        this.saveStudentGraduationUseCase = saveStudentGraduationUseCase;
        this.findStudentGraduationByIdUseCase = findStudentGraduationByIdUseCase;
        this.findByStudentIdUseCase = findByStudentIdUseCase;
        this.findByInstructorIdUseCase = findByInstructorIdUseCase;
        this.studentGraduationMapper = studentGraduationMapper;
    }

    @Operation(summary = "Registrar graduação de aluno", description = "Registra uma nova graduação atribuída a um aluno por um instrutor em determinada data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Graduação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<StudentGraduationResponse> create(@RequestBody @Valid CreateStudentGraduationRequest request) {
        Student student = new Student(request.studentId(), null, true, 0, 0, false, null, null, null, null);
        Instructor instructor = new Instructor(request.instructorId(), null, null, null, null, null, null);
        Graduation graduation = new Graduation(request.graduationId(), null, 0, null, null);

        StudentGraduation studentGraduation = new StudentGraduation(
                student,
                instructor,
                graduation,
                request.graduationDay()
        );

        StudentGraduation saved = saveStudentGraduationUseCase.execute(studentGraduation);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentGraduationMapper.fromDomain(saved));
    }

    @Operation(summary = "Buscar graduação de aluno por ID", description = "Retorna os detalhes de um registro de graduação pelo seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentGraduationResponse> findById(@PathVariable Long id) {
        StudentGraduation studentGraduation = findStudentGraduationByIdUseCase.execute(id);
        return ResponseEntity.ok(studentGraduationMapper.fromDomain(studentGraduation));
    }

    @Operation(summary = "Listar histórico de graduações de um aluno", description = "Retorna todas as graduações conquistadas por um determinado aluno.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentGraduationResponse>> findByStudentId(@PathVariable Long studentId) {
        List<StudentGraduationResponse> list = findByStudentIdUseCase.execute(studentId).stream()
                .map(studentGraduationMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Listar graduações concedidas por um instrutor", description = "Retorna todas as graduações concedidas por um instrutor específico.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<StudentGraduationResponse>> findByInstructorId(@PathVariable Long instructorId) {
        List<StudentGraduationResponse> list = findByInstructorIdUseCase.execute(instructorId).stream()
                .map(studentGraduationMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }
}
