package com.losmessias.leherer.domain;

import com.losmessias.leherer.domain.embedabble.ProfessorSubjectPK;
import com.losmessias.leherer.domain.enumeration.SubjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor_subject")
public class ProfessorSubject {

    @EmbeddedId
    private ProfessorSubjectPK id;

    @ManyToOne
    @MapsId("professorId")
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "status")
    private SubjectStatus status;

    public ProfessorSubject(Professor professor, Subject subject) {
        this.professor = professor;
        this.subject = subject;
    }

}
