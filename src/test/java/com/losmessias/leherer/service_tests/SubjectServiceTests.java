package com.losmessias.leherer.service_tests;

import com.losmessias.leherer.domain.Professor;
import com.losmessias.leherer.domain.Subject;
import com.losmessias.leherer.repository.SubjectRepository;
import com.losmessias.leherer.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTests {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void testGetAllSubjects() {
        List<Subject> subjects = new ArrayList<Subject>();
        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Physics"));
        when(subjectRepository.findAll()).thenReturn(subjects);

        assertEquals(subjects, subjectService.getAllSubjects());
    }

    @Test
    void testGetAllSubjectsEmpty() {
        List<Subject> subjects = new ArrayList<Subject>();
        when(subjectRepository.findAll()).thenReturn(subjects);
        assertEquals(subjects, subjectService.getAllSubjects());
    }

//    @Test
//    void testGetSubjectTaughtByAProfessor() {
//
//        Professor professor = new Professor("John", "Doe");
//        Subject subject = new Subject( "Math");
//        professor.addSubject(subject);
//
//    }

}
