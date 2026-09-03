package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentGraduationEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentGraduationMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.GraduationMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.InstructorMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringGraduationJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringInstructorJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringStudentGraduationJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringStudentJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentGraduationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentGraduationRepositoryImpl implements StudentGraduationRepository {

    private final SpringStudentGraduationJpaRepository jpaRepository;
    private final StudentGraduationMapper mapper;
    private final SpringStudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;
    private final SpringInstructorJpaRepository instructorJpaRepository;
    private final InstructorMapper instructorMapper;
    private final SpringGraduationJpaRepository graduationJpaRepository;
    private final GraduationMapper graduationMapper;

    public StudentGraduationRepositoryImpl(
            SpringStudentGraduationJpaRepository jpaRepository,
            StudentGraduationMapper mapper,
            SpringStudentJpaRepository studentJpaRepository,
            StudentMapper studentMapper,
            SpringInstructorJpaRepository instructorJpaRepository,
            InstructorMapper instructorMapper,
            SpringGraduationJpaRepository graduationJpaRepository,
            GraduationMapper graduationMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
        this.instructorJpaRepository = instructorJpaRepository;
        this.instructorMapper = instructorMapper;
        this.graduationJpaRepository = graduationJpaRepository;
        this.graduationMapper = graduationMapper;
    }

    private StudentGraduation mapToDomainWithRelations(StudentGraduationEntity entity) {
        if (entity == null) return null;

        Student student = null;
        if (entity.getStudentId() != null) {
            student = studentJpaRepository.findById(entity.getStudentId())
                    .map(studentMapper::toDomain)
                    .orElseGet(() -> new Student(entity.getStudentId(), null, true, 0, 0, false, null, null, null, null));
        }

        Instructor instructor = null;
        if (entity.getInstructorId() != null) {
            instructor = instructorJpaRepository.findById(entity.getInstructorId())
                    .map(instructorMapper::toDomain)
                    .orElseGet(() -> new Instructor(entity.getInstructorId(), null, null, null, null, null, null));
        }

        Graduation graduation = null;
        if (entity.getGraduationId() != null) {
            graduation = graduationJpaRepository.findById(entity.getGraduationId())
                    .map(graduationMapper::toDomain)
                    .orElseGet(() -> new Graduation(entity.getGraduationId(), null, 0, null, null));
        }

        return mapper.toDomain(entity, student, instructor, graduation);
    }

    @Override
    public StudentGraduation save(StudentGraduation studentGraduation) {
        StudentGraduationEntity entity = mapper.toEntity(studentGraduation);
        StudentGraduationEntity saved = jpaRepository.save(entity);
        return mapToDomainWithRelations(saved);
    }

    @Override
    public Optional<StudentGraduation> findById(Long id) {
        return jpaRepository.findById(id).map(this::mapToDomainWithRelations);
    }

    @Override
    public List<StudentGraduation> findByStudentId(Long studentId) {
        return jpaRepository.findByStudentId(studentId).stream()
                .map(this::mapToDomainWithRelations)
                .toList();
    }

    @Override
    public List<StudentGraduation> findByInstructorId(Long instructorId) {
        return jpaRepository.findByInstructorId(instructorId).stream()
                .map(this::mapToDomainWithRelations)
                .toList();
    }
}
