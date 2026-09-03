package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class GraduationException extends DomainException {

    public GraduationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public GraduationException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

