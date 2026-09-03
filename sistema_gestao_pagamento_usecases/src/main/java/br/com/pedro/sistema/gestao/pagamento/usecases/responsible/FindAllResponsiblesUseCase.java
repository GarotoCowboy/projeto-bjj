package br.com.pedro.sistema.gestao.pagamento.usecases.responsible;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;


public class FindAllResponsiblesUseCase {

    private final ResponsibleRepository responsibleRepository;

    public FindAllResponsiblesUseCase(ResponsibleRepository responsibleRepository){
        this.responsibleRepository = responsibleRepository;
    }

        public List<Responsible> execute(){
        return responsibleRepository.findAll();
    }

}
