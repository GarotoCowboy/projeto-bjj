package br.com.pedro.sistema.gestao.pagamento.usecases.graduation;

import java.util.List;

import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;

public class FindAllGraduationsUseCase {

           private final GraduationRepository graduationRepository;

    public FindAllGraduationsUseCase(GraduationRepository graduationRepository){
        this.graduationRepository = graduationRepository;
    }

    public List<Graduation> execute(){
        return graduationRepository.findAll();
    }
}

