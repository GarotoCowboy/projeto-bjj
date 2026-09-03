package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.GraduationEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.GraduationMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringGraduationJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.Belt;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GraduationRepositoryImpl implements GraduationRepository {

    private final SpringGraduationJpaRepository jpaRepository;
    private final GraduationMapper mapper;

    public GraduationRepositoryImpl(SpringGraduationJpaRepository jpaRepository, GraduationMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Graduation save(Graduation graduation) {
        GraduationEntity entity = mapper.toEntity(graduation);
        GraduationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Graduation> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Graduation> findByBeltAndDegree(Belt belt, int degree) {
        return jpaRepository.findByBeltAndDegree(belt, degree).map(mapper::toDomain);
    }

    @Override
    public List<Graduation> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
