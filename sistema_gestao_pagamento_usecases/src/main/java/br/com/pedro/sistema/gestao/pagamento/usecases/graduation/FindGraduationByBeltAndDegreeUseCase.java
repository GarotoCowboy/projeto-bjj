package br.com.pedro.sistema.gestao.pagamento.usecases.graduation;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.GraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;

public class FindGraduationByBeltAndDegreeUseCase {

            private final GraduationRepository graduationRepository;

    public FindGraduationByBeltAndDegreeUseCase(GraduationRepository graduationRepository){
        this.graduationRepository = graduationRepository;
    }

       public Graduation execute(Belt belt,int degree){
        if(belt == null){
            throw new GraduationException(ErrorCode.GR_0001);
        }
         if(degree < 0 || degree > 4 ){
            throw new GraduationException(ErrorCode.GR_0002);
        }
        return graduationRepository.findByBeltAndDegree(belt,degree).orElseThrow(
            () -> new GraduationException(ErrorCode.GR_0003));
    }
}

