package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public class StudentGraduationException extends DomainException {

    public StudentGraduationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StudentGraduationException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

