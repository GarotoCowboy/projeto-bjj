package br.com.pedro.sistema.gestao.pagamento.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão de Academia de BJJ")
                        .version("v1.0.0")
                        .description("API REST para gestão de alunos, instrutores, mensalidades, graduações e responsáveis de academia de Jiu-Jitsu.")
                        .contact(new Contact()
                                .name("Pedro - BJJ System")
                                .email("suporte@bjj.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .tags(List.of(
                        new Tag().name("Alunos").description("Operações para gerenciamento de alunos"),
                        new Tag().name("Instrutores").description("Operações para gerenciamento de instrutores"),
                        new Tag().name("Responsáveis").description("Operações para gerenciamento de responsáveis por alunos"),
                        new Tag().name("Graduações").description("Operações para catálogo de faixas e graus"),
                        new Tag().name("Mensalidades").description("Operações para controle financeiro e pagamentos de mensalidades"),
                        new Tag().name("Graduações de Alunos").description("Histórico e atribuição de faixas aos alunos")
                ));
    }
}
