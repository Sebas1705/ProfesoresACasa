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
    private int totalScore;

    @Column(name = "NUM_RANKINGS", nullable=false)
    private int numRankings;

    public Ranking(){this(0,0);}

    public Ranking(int totalScore, int numRankings){
        this.totalScore=totalScore;
        this.numRankings=numRankings;
    }

    public int getAverage(){
        if(numRankings==0)return 0;
        return totalScore/numRankings;
    }

    public void incrementScore(int punt){
        this.totalScore+=punt;
        this.numRankings++;
    }
    
}
