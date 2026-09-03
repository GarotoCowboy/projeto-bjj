package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class ResponsibleException extends DomainException {

    public ResponsibleException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResponsibleException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

