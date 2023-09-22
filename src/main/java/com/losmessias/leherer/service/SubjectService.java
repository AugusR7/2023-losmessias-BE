package com.losmessias.leherer.service;

import com.losmessias.leherer.domain.Professor;
import com.losmessias.leherer.domain.ProfessorSubject;
import com.losmessias.leherer.domain.Subject;
import com.losmessias.leherer.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject addProfessorTo(ProfessorSubject professorSubject) {
        Subject subject = professorSubject.getSubject();
        subject.addProfessor(professorSubject);
        return subjectRepository.save(subject);
    }

//    public Subject getSubjectById(Long id) {
//        return subjectRepository.findById(id).orElse(null);
//    }
//
//    public Subject getSubjectByName(String name) {
//        return subjectRepository.findByName(name);
//    }


}
