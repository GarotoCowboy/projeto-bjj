package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.MembershipEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.MembershipMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringMembershipJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Membership;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.enums.PaymentStatus;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringStudentJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.MembershipRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MembershipRepositoryImpl implements MembershipRepository {

    private final SpringMembershipJpaRepository jpaRepository;
    private final MembershipMapper mapper;
    private final SpringStudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;

    public MembershipRepositoryImpl(
            SpringMembershipJpaRepository jpaRepository,
            MembershipMapper mapper,
            SpringStudentJpaRepository studentJpaRepository,
            StudentMapper studentMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
    }

    private Membership mapToDomainWithStudent(MembershipEntity entity) {
        if (entity == null) return null;
        Student student = null;
        if (entity.getStudentId() != null) {
            student = studentJpaRepository.findById(entity.getStudentId())
                    .map(studentMapper::toDomain)
                    .orElseGet(() -> new Student(entity.getStudentId(), null, true, 0, 0, false, null, null, null, null));
        }
        return mapper.toDomain(entity, student);
    }

    @Override
    public Membership save(Membership membership) {
        MembershipEntity entity = mapper.toEntity(membership);
        MembershipEntity saved = jpaRepository.save(entity);
        return mapToDomainWithStudent(saved);
    }

    @Override
    public Optional<Membership> findById(Long id) {
        return jpaRepository.findById(id).map(this::mapToDomainWithStudent);
    }

    @Override
    public List<Membership> findByStudentId(Long studentId) {
        return jpaRepository.findByStudentId(studentId).stream()
                .map(this::mapToDomainWithStudent)
                .toList();
    }

    @Override
    public List<Membership> findByStatus(PaymentStatus status) {
        return jpaRepository.findByStatus(status).stream()
                .map(this::mapToDomainWithStudent)
                .toList();
    }

    @Override
    public List<Membership> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomainWithStudent)
                .toList();
    }
}
