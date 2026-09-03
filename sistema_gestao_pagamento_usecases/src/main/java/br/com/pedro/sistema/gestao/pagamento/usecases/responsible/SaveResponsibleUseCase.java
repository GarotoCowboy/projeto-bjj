package br.com.pedro.sistema.gestao.pagamento.usecases.responsible;

import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ErrorCode;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.ResponsibleException;
import br.com.pedro.sistema.gestao.pagamento.core.exceptions.StudentException;
import br.com.pedro.sistema.gestao.pagamento.core.models.Responsible;
import br.com.pedro.sistema.gestao.pagamento.core.models.Student;
import br.com.pedro.sistema.gestao.pagamento.repository.ResponsibleRepository;
import br.com.pedro.sistema.gestao.pagamento.repository.StudentRepository;

public class SaveResponsibleUseCase {

    private final ResponsibleRepository responsibleRepository;
    private final StudentRepository studentRepository;

    public SaveResponsibleUseCase(ResponsibleRepository responsibleRepository, StudentRepository studentRepository) {
        this.responsibleRepository = responsibleRepository;
        this.studentRepository = studentRepository;
    }

    public Responsible execute(Responsible responsible) {
        if (responsible == null) {
            throw new ResponsibleException(ErrorCode.RE_0001);
        }

        if (responsible.getStudentProfile() != null && responsible.getStudentProfile().getId() != null) {
            Student student = studentRepository.findById(responsible.getStudentProfile().getId())
                    .orElseThrow(() -> new StudentException(ErrorCode.ST_0006));
            responsible.setStudentProfile(student);
        }

        return responsibleRepository.save(responsible);
    }
}
