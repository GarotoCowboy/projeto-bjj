package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class MembershipException extends DomainException {

    public MembershipException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MembershipException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

