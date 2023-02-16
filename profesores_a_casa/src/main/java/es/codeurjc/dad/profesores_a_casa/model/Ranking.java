package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;
import lombok.*;

@Setter
@Getter
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

    public Ranking(){this(0.00,0);}

    public Ranking(double totalScore, int numRankings){
        this.totalScore=totalScore;
        this.numRankings=numRankings;
    }

    public double getAverage(){
        return totalScore/numRankings;
    }
    
}
