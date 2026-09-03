package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.ResponsibleEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.ResponsibleMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringResponsibleJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringStudentJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;

@Component
public class ResponsibleRepositoryImpl implements ResponsibleRepository {

    private final SpringResponsibleJpaRepository jpaRepository;
    private final ResponsibleMapper mapper;
    private final SpringStudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;

    public ResponsibleRepositoryImpl(
            SpringResponsibleJpaRepository jpaRepository,
            ResponsibleMapper mapper,
            SpringStudentJpaRepository studentJpaRepository,
            StudentMapper studentMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
    }

    private Responsible mapToDomainWithDependents(ResponsibleEntity entity) {
        if (entity == null) return null;
        List<Student> dependents = studentJpaRepository.findByResponsibleId(entity.getId()).stream()
                .map(studentMapper::toDomain)
                .toList();
        return mapper.toDomain(entity, dependents);
    }

    @Override
    public Responsible save(Responsible responsible) {
        ResponsibleEntity entity = mapper.toEntity(responsible);
        ResponsibleEntity saved = jpaRepository.save(entity);
        return mapToDomainWithDependents(saved);
    }

    @Override
    public Optional<Responsible> findById(Long id) {
        return jpaRepository.findById(id).map(this::mapToDomainWithDependents);
    }

    @Override
    public List<Responsible> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomainWithDependents)
                .toList();
    }
}
