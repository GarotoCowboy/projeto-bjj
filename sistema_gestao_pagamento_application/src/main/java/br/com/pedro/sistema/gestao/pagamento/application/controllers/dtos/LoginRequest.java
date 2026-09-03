package br.com.pedro.sistema.gestao.pagamento.application.controllers.dtos;

public record LoginRequest(
    String username,
    String password
) {

}
