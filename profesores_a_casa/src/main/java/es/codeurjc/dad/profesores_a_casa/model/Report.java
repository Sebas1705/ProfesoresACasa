package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REPORT_ID", unique = true, nullable=false)
    private long id;

    @JsonIgnore
    @ManyToOne
    private User author;

    @JsonIgnore
    @ManyToOne
    private Post post;

    @Column(name = "MOTIVE", nullable=false)
    private String motive;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    public Report(){this(null,null);}

    public Report(String motive,String description){
        this.motive=motive;
        this.description=description;
    }
}
