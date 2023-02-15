package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
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

    @Column(name="DESCRIPTION")
    private String description;

    public Contract(){}

    public Contract(String description){
        this.description = description;
    }
}
