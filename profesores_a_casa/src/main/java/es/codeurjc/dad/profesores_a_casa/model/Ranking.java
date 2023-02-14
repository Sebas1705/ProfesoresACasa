package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity

public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RAKING_ID", unique = true, nullable=false)
    private long id;

    @OneToOne(mappedBy="ranking")
    private Post post;

    @Column(name = "SCORE", nullable=false)
    private double score;

    public Ranking(){}
}
