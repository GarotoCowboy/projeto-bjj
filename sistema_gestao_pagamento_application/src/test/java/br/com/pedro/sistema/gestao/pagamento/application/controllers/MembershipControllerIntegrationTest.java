package br.com.pedro.sistema.gestao.pagamento.application.controllers;

import br.com.pedro.sistema.gestao.pagamento.application.security.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MembershipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenService jwtTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private String token;

    @BeforeEach
    void setUp() {
        token = "Bearer " + jwtTokenService.generateToken("admin.test");
    }

    @Test
    @DisplayName("Should create, pay and cancel membership with studentName populated")
    void shouldCreateAndPayMembership() throws Exception {
        // 1. Cadastrar um aluno
        String studentJson = """
                {
                    "name": "Renzo Gracie",
                    "email": "renzo@gracie.com",
                    "phoneNumber": "11911112222",
                    "birthday": "1995-05-10",
                    "weight": 82,
                    "height": 180,
                    "isHealthProblem": false
                }
                """;

        MvcResult studentResult = mockMvc.perform(post("/api/v1/bjj/students")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode studentNode = objectMapper.readTree(studentResult.getResponse().getContentAsString());
        long studentId = studentNode.get("id").asLong();

        // 2. Criar mensalidade para o aluno
        String membershipJson = """
                {
                    "studentId": %d,
                    "amount": 250.00,
                    "contractTime": "MONTHLY",
                    "dueDate": "2026-10-10"
                }
                """.formatted(studentId);

        MvcResult membershipResult = mockMvc.perform(post("/api/v1/bjj/memberships")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(membershipJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value("Renzo Gracie"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andReturn();

        JsonNode membershipNode = objectMapper.readTree(membershipResult.getResponse().getContentAsString());
        long membershipId = membershipNode.get("id").asLong();

        // 3. Pagar a mensalidade
        mockMvc.perform(patch("/api/v1/bjj/memberships/{id}/pay", membershipId)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.studentName").value("Renzo Gracie"))
                .andExpect(jsonPath("$.paymentDate").isNotEmpty());

        // 4. Criar uma segunda mensalidade para testar cancelamento
        MvcResult secondResult = mockMvc.perform(post("/api/v1/bjj/memberships")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(membershipJson))
                .andExpect(status().isCreated())
                .andReturn();

        long secondId = objectMapper.readTree(secondResult.getResponse().getContentAsString()).get("id").asLong();

        // 5. Cancelar a segunda mensalidade
        mockMvc.perform(patch("/api/v1/bjj/memberships/{id}/cancel", secondId)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"))
                .andExpect(jsonPath("$.studentName").value("Renzo Gracie"));
    }
}
