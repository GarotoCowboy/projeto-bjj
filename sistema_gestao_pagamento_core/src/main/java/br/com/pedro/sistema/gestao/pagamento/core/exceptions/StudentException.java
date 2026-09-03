package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class StudentException extends DomainException {

    public StudentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StudentException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

