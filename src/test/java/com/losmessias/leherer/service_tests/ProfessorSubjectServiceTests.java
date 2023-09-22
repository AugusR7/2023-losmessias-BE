package com.losmessias.leherer.service_tests;

import com.losmessias.leherer.domain.ProfessorSubject;
import com.losmessias.leherer.repository.ProfessorSubjectRepository;
import com.losmessias.leherer.service.ProfessorSubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProfessorSubjectServiceTests {
    @Mock
    private ProfessorSubjectRepository professorSubjectRepository;

    @InjectMocks
    private ProfessorSubjectService professorSubjectService;

    @Test
    void testGetAllProfessorSubjects() {
        List<ProfessorSubject> professorSubjects = new ArrayList<>();

    }
//    @Test
//    void testGetAllSubjects() {
//        List<Subject> subjects = new ArrayList<Subject>();
//        subjects.add(new Subject("Math"));
//        subjects.add(new Subject("Physics"));
//        when(subjectRepository.findAll()).thenReturn(subjects);
//
//        assertEquals(subjects, subjectService.getAllSubjects());
//    }

}
