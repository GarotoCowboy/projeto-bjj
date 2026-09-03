package br.com.pedro.sistema.gestao.pagamento.usecases.studentGraduation;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.GraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.InstructorException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentGraduationException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Graduation;
import br.com.pedro.sistema.gestao.pagamento.core.models.Instructor;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.core.models.StudentGraduation;
import br.com.pedro.sistema.gestao.pagamento.repository.GraduationRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.InstructorRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentGraduationRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class SaveStudentGraduationUseCase {

    private final StudentGraduationRepository studentGraduationRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final GraduationRepository graduationRepository;

    public SaveStudentGraduationUseCase(
            StudentGraduationRepository studentGraduationRepository,
            StudentRepository studentRepository,
            InstructorRepository instructorRepository,
            GraduationRepository graduationRepository) {
        this.studentGraduationRepository = studentGraduationRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.graduationRepository = graduationRepository;
    }

    public StudentGraduation execute(StudentGraduation studentGraduation) {
        if (studentGraduation == null) {
            throw new StudentGraduationException(ErrorCode.SG_0001);
        }

        if (studentGraduation.getStudent() == null || studentGraduation.getStudent().getId() == null) {
            throw new StudentGraduationException(ErrorCode.SG_0001);
        }
        Student student = studentRepository.findById(studentGraduation.getStudent().getId())
                .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));

        if (studentGraduation.getInstructor() == null || studentGraduation.getInstructor().getId() == null) {
            throw new StudentGraduationException(ErrorCode.SG_0002);
        }
        Instructor instructor = instructorRepository.findById(studentGraduation.getInstructor().getId())
                .orElseThrow(() -> new InstructorException(ErrorCode.IN_0007));

        if (studentGraduation.getGraduation() == null || studentGraduation.getGraduation().getId() == null) {
            throw new StudentGraduationException(ErrorCode.SG_0003);
        }
        Graduation graduation = graduationRepository.findById(studentGraduation.getGraduation().getId())
                .orElseThrow(() -> new GraduationException(ErrorCode.GR_0004));

        studentGraduation.setStudent(student);
        studentGraduation.setInstructor(instructor);
        studentGraduation.setGraduation(graduation);

        return studentGraduationRepository.save(studentGraduation);
    }
}
