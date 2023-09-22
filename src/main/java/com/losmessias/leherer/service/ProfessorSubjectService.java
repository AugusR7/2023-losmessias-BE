package com.losmessias.leherer.service;

import com.losmessias.leherer.domain.Professor;
import com.losmessias.leherer.domain.ProfessorSubject;
import com.losmessias.leherer.domain.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorSubjectService {

    private final ProfessorService professorService;

    private final SubjectService subjectService;

    public void createAssociationBetween(Professor professor, Subject subject) {
        ProfessorSubject professorSubject = new ProfessorSubject(professor, subject);
        professorService.addSubjectTo(professorSubject);
        subjectService.addProfessorTo(professorSubject);
    }
}
