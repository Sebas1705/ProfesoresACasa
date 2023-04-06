package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Contract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTRACT_ID", unique = true, nullable=false)
    private long id;

    @JsonIgnore
    @ManyToOne
    private Post post;

    @JsonIgnore
    @ManyToOne
    private User teacher;

    @JsonIgnore
    @ManyToOne
    private User student;

    @Column(name="STUDENT_WANT_TO_DELETE")
    private boolean studentWantToDelete;

    @Column(name="TEACHER_WANT_TO_DELETE")
    private boolean teacherWantToDelete;

    public Contract(){
        this.studentWantToDelete=false;
        this.teacherWantToDelete=false;
    }

}
