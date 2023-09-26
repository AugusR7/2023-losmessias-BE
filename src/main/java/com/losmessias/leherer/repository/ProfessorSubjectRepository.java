package com.losmessias.leherer.repository;

import com.losmessias.leherer.domain.ProfessorSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorSubjectRepository extends JpaRepository<ProfessorSubject, Long> {

    List<ProfessorSubject> findByProfessorId(Long professorId);

    ProfessorSubject findByProfessorIdAndSubject_Id(Long professorId, Long subjectId);
}
