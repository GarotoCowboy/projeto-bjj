package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class InstructorException extends DomainException {

    public InstructorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InstructorException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

