package com.losmessias.leherer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<ProfessorSubject> subjects;

    public Subject(String name) {
        this.name = name;
    }

    public void addProfessor(ProfessorSubject subject) {
        this.subjects.add(subject);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
