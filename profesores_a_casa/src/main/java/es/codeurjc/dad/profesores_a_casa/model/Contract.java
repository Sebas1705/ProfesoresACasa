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
    @Column(name = "POST", nullable=false)
    private Post post;

    @ManyToOne
    @Column(name = "TEACHER", nullable=false)
    private User teacher;

    @ManyToOne
    @Column(name = "ESTUDENT", nullable=false)
    private User estudent;

    public Contract(){}
}
