package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.InstructorEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.InstructorMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringInstructorJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InstructorRepositoryImpl implements InstructorRepository {

    private final SpringInstructorJpaRepository jpaRepository;
    private final InstructorMapper mapper;

    public InstructorRepositoryImpl(SpringInstructorJpaRepository jpaRepository, InstructorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Instructor save(Instructor instructor) {
        InstructorEntity entity = mapper.toEntity(instructor);
        InstructorEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Instructor> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public List<Instructor> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
