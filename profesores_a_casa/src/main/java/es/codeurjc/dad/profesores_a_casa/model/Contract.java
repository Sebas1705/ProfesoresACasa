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
    private long contractId;

    @Column(name = "POST_REFERENCE_ID", nullable=false)
    private long postReferenceId;

    @Column(name = "TEACHERS_ID", nullable=false)
    private long teachersId;

    @Column(name = "ESTUDENTS_ID", nullable=false)
    private long estudentsId;

    public Contract(){}
}
