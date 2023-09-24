package com.losmessias.leherer.repository;

import com.losmessias.leherer.domain.ProfessorSubject;
import com.losmessias.leherer.domain.embedabble.ProfessorSubjectPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorSubjectRepository extends JpaRepository<ProfessorSubject, ProfessorSubjectPK> {

}
