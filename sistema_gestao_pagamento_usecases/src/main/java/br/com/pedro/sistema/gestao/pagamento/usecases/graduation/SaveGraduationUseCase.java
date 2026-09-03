package br.com.pedro.sistema.gestao.pagamento.usecases.graduation;

import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;

public class SaveGraduationUseCase {
    private final GraduationRepository graduationRepository;

    public SaveGraduationUseCase(GraduationRepository graduationRepository){
        this.graduationRepository = graduationRepository;
    }

    public Graduation execute(Graduation graduation){
        return graduationRepository.save(graduation);
    }

}
