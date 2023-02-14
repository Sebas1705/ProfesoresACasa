package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "TOTAL_SCORE", nullable=false)
    private double totalScore;

    @Column(name = "NUM_RANKINGS", nullable=false)
    private int numRankings;

    public Ranking(){
        this.totalScore=0.00;
        this.numRankings=0;
    }

    public double getAverage(){
        return totalScore/numRankings;
    }
    
}
