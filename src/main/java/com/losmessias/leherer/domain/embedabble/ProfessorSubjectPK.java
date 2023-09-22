package com.losmessias.leherer.domain.embedabble;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
public class ProfessorSubjectPK implements Serializable {

    @Column(name = "professor_id")
    private Long professorId;

    @Column(name = "subject_id")
    private Long subjectId;
}
