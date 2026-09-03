package br.com.pedro.sistema.gestao.pagamento.application.persistence.adapters;

import br.com.pedro.sistema.gestao.pagamento.application.persistence.entities.StudentEntity;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.mappers.StudentMapper;
import br.com.pedro.sistema.gestao.pagamento.application.persistence.repositories.SpringStudentJpaRepository;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final SpringStudentJpaRepository jpaRepository;
    private final StudentMapper mapper;

    public StudentRepositoryImpl(SpringStudentJpaRepository jpaRepository, StudentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }



    @Override
    public Student save(Student student) {
        StudentEntity entity = mapper.toEntity(student);
       StudentEntity saved =  jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Student update(Long id, Student student) {
        StudentEntity entity = mapper.toEntity(student);
        entity.setId(id);

        StudentEntity updated =  jpaRepository.save(entity);

        return mapper.toDomain(updated);
    }

    @Override
    public Student inactiveStudent(Long id) {
        StudentEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));
        entity.setActive(false);

        StudentEntity updated =  jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Student activeStudent(Long id) {
        StudentEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));
        entity.setActive(true);

        StudentEntity updated =  jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Student> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Student> findAllActive() {
        return jpaRepository.findByIsActiveTrue().stream().map(mapper::toDomain).toList();
    }
}
