package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPORT_ID", unique = true, nullable=false)
    private long reportId;

    @Column(name = "AUTHOR_ID", nullable=false)
    private long authorId;

    @Column(name = "OFFER_ID", nullable=false)
    private long offerId;

    @Column(name = "MOTIVE", nullable=false)
    private String motive;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    public Report(){}
}
