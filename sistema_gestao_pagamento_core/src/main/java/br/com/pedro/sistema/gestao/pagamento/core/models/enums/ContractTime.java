package br.com.pedro.sistema.gestao.pagamento.core.models.enums;

public enum ContractTime {
MONTHLY(1),
TRIMESTER(3),
SEMI_ANNUAL(6),
ANNUAL(12);

private final int durationInMonths;

ContractTime(int durationInMonths){
    this.durationInMonths = durationInMonths;
}

public int getDurationInMonths(){
    return durationInMonths;
}
}
