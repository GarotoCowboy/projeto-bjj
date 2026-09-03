package br.com.pedro.sistema.gestao.pagamento.usecases.responsible;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ResponsibleException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;

public class FindResponsibleByIdUseCase {

            private final ResponsibleRepository responsibleRepository;

    public FindResponsibleByIdUseCase(ResponsibleRepository responsibleRepository){
        this.responsibleRepository = responsibleRepository;
    }

  public Responsible execute(Long id){
        if(id == null){
            throw new ResponsibleException(ErrorCode.GE_0001);
        }
        return responsibleRepository.findById(id).orElseThrow(
            () -> new ResponsibleException(ErrorCode.RE_0004));
    }
}
