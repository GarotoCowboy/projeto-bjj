package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class PersonException extends DomainException {

    public PersonException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PersonException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

