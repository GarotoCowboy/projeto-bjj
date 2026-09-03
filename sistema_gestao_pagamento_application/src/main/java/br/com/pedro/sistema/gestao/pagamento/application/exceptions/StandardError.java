package br.com.pedro.sistema.gestao.pagamento.application.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record StandardError(
        LocalDateTime timestamp,
        Integer status,
        String errorCode,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    public StandardError(Integer status, String errorCode, String message, String path) {
        this(LocalDateTime.now(), status, errorCode, message, path, null);
    }
}
