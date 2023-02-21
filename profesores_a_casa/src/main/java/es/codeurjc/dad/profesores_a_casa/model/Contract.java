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

<<<<<<< HEAD
    public Contract(){}
=======
    

    public Contract(){
        
    }

    
>>>>>>> 52cc13f5d4a034cddff2d31b9d44a8c9b556721e
}
