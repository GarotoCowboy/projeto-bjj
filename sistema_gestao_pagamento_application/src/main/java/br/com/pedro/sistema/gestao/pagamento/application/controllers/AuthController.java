package br.com.pedro.sistema.gestao.pagamento.application.controllers;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.LoginRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.LoginResponse;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.RefreshTokenRequest;
import br.com.pedro.sistema.gestao.pagamento.application.security.JwtTokenService;
import br.com.pedro.sistema.gestao.pagamento.application.security.RefreshTokenService;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.FindInstructorByUsernameUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.CreateInstructorRequest;
import br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos.InstructorResponse;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.InstructorMapper;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Person;
import br.com.pedro.sistema.gestao.pagamento.usecases.instructor.SaveInstructorUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints de login e autenticação")
public class AuthController {
    private final FindInstructorByUsernameUseCase findInstructorByUsernameUseCase;
    private final SaveInstructorUseCase saveInstructorUseCase;
    private final InstructorMapper instructorMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(FindInstructorByUsernameUseCase findInstructorByUsernameUseCase,
                          SaveInstructorUseCase saveInstructorUseCase,
                          InstructorMapper instructorMapper,
                          PasswordEncoder passwordEncoder,
                          JwtTokenService jwtTokenService,
                          RefreshTokenService refreshTokenService) {
        this.findInstructorByUsernameUseCase = findInstructorByUsernameUseCase;
        this.saveInstructorUseCase = saveInstructorUseCase;
        this.instructorMapper = instructorMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }
    @PostMapping("/login")
    @Operation(summary = "Realiza login e devolve o Access Token e o Refresh Token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var instructor = findInstructorByUsernameUseCase.execute(request.username());
        if (!passwordEncoder.matches(request.password(), instructor.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        // 1. Gera o access token (curto)
        String accessToken = jwtTokenService.generateToken(instructor.getUsername());
        
        // 2. Cria o refresh token (30 dias no banco)
        var refreshToken = refreshTokenService.createRefreshToken(instructor.getUsername());
        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken.getToken(), jwtTokenService.getExpiration()));
    }
    @PostMapping("/refresh")
    @Operation(summary = "Renova o Access Token usando o Refresh Token")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    // Gera um novo access token sem precisar pedir a senha novamente
                    String newAccessToken = jwtTokenService.generateToken(refreshToken.getUsername());
                    return ResponseEntity.ok(new LoginResponse(newAccessToken, refreshToken.getToken(), jwtTokenService.getExpiration()));
                })
                .orElseGet(() -> ResponseEntity.status(403).build());
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastra um novo instrutor (acesso público inicial)")
    public ResponseEntity<InstructorResponse> register(@RequestBody @Valid CreateInstructorRequest request) {
        Person person = new Person(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.birthday()
        );

        String hashedPassword = passwordEncoder.encode(request.password());
        Instructor instructor = new Instructor(
                person,
                request.username(),
                hashedPassword,
                request.belt()
        );

        Instructor saved = saveInstructorUseCase.execute(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorMapper.fromDomain(saved));
    }
}