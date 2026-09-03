package br.com.pedro.sistema.gestao.pagamento.usecases.graduation;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.GraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;

public class FindGraduationByIdUseCase {
 private final GraduationRepository graduationRepository;

    public FindGraduationByIdUseCase(GraduationRepository graduationRepository){
        this.graduationRepository = graduationRepository;
    }

       public Graduation execute(Long id){
        if(id == null){
            throw new GraduationException(ErrorCode.GR_0003);
        }
        
        return graduationRepository.findById(id).orElseThrow(
            () -> new GraduationException(ErrorCode.GR_0004));
    }
}
