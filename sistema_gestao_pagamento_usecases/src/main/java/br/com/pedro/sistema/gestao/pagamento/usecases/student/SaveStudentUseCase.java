package br.com.pedro.sistema.gestao.pagamento.usecases.student;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ResponsibleException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class SaveStudentUseCase {

    private final StudentRepository studentRepository;
    private final ResponsibleRepository responsibleRepository;

    public SaveStudentUseCase(StudentRepository studentRepository, ResponsibleRepository responsibleRepository) {
        this.studentRepository = studentRepository;
        this.responsibleRepository = responsibleRepository;
    }

    public Student execute(Student student) {
        if (student == null) {
            throw new StudentException(ErrorCode.ST_0001);
        }

        if (student.getResponsible() != null && student.getResponsible().getId() != null) {
            Responsible responsible = responsibleRepository.findById(student.getResponsible().getId())
                    .orElseThrow(() -> new ResponsibleException(ErrorCode.RE_0004));
            student.setResponsible(responsible);
        }

        return studentRepository.save(student);
    }
}
