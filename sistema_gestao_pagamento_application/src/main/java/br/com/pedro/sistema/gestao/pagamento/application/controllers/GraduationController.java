package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateGraduationRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.GraduationResponse;
import br.com.pedro.sistema.gestao.pagamento.application.exceptions.StandardError;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.GraduationMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindAllGraduationsUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindGraduationByBeltAndDegreeUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.FindGraduationByIdUseCase;
import br.com.pedro.sistema.gestao.pagamento.usecases.graduation.SaveGraduationUseCase;
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

@Tag(name = "Graduações", description = "Endpoints para cadastro e consulta de faixas e graus de Jiu-Jitsu")
@RestController
@RequestMapping("/api/v1/bjj/graduations")
public class GraduationController {

    private final SaveGraduationUseCase saveGraduationUseCase;
    private final FindAllGraduationsUseCase findAllGraduationsUseCase;
    private final FindGraduationByIdUseCase findGraduationByIdUseCase;
    private final FindGraduationByBeltAndDegreeUseCase findGraduationByBeltAndDegreeUseCase;
    private final GraduationMapper graduationMapper;

    public GraduationController(
            SaveGraduationUseCase saveGraduationUseCase,
            FindAllGraduationsUseCase findAllGraduationsUseCase,
            FindGraduationByIdUseCase findGraduationByIdUseCase,
            FindGraduationByBeltAndDegreeUseCase findGraduationByBeltAndDegreeUseCase,
            GraduationMapper graduationMapper) {
        this.saveGraduationUseCase = saveGraduationUseCase;
        this.findAllGraduationsUseCase = findAllGraduationsUseCase;
        this.findGraduationByIdUseCase = findGraduationByIdUseCase;
        this.findGraduationByBeltAndDegreeUseCase = findGraduationByBeltAndDegreeUseCase;
        this.graduationMapper = graduationMapper;
    }

    @Operation(summary = "Cadastrar uma nova graduação", description = "Cadastra uma combinação única de faixa e grau no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Graduação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<GraduationResponse> create(@RequestBody @Valid CreateGraduationRequest request) {
        Graduation graduation = new Graduation(
                request.belt(),
                request.degree()
        );

        Graduation saved = saveGraduationUseCase.execute(graduation);
        return ResponseEntity.status(HttpStatus.CREATED).body(graduationMapper.fromDomain(saved));
    }

    @Operation(summary = "Listar todas as graduações", description = "Retorna todas as faixas e graus disponíveis no catálogo.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<GraduationResponse>> listAll() {
        List<GraduationResponse> list = findAllGraduationsUseCase.execute().stream()
                .map(graduationMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar graduação por ID", description = "Retorna uma graduação específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Graduação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Graduação não encontrada", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GraduationResponse> findById(@PathVariable Long id) {
        Graduation graduation = findGraduationByIdUseCase.execute(id);
        return ResponseEntity.ok(graduationMapper.fromDomain(graduation));
    }

    @Operation(summary = "Buscar graduação por faixa e grau", description = "Filtra a graduação pela cor da faixa e quantidade de graus (0 a 4).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Graduação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Graduação não encontrada", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<GraduationResponse> findByBeltAndDegree(
            @RequestParam Belt belt,
            @RequestParam int degree) {
        Graduation graduation = findGraduationByBeltAndDegreeUseCase.execute(belt, degree);
        return ResponseEntity.ok(graduationMapper.fromDomain(graduation));
    }
}
