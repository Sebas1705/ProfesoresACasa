package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

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

    @ManyToOne
    private Post post;

    @ManyToOne
    private User teacher;

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
