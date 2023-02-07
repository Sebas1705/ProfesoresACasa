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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID", unique = true, nullable=false)
    private long id;

    @ManyToOne
    @Column(name = "OWNER_USER", nullable = false)
    private User ownerUser;

    @OneToMany
    @Column(name = "CONTRACT")
    private List<Contract> contract;

    @OneToMany
    @Column(name = "REPORTS")
    private List<Report> reports;

    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "RANKING", nullable=false)
    private Ranking ranking;

    @Column(name = "TITLE", nullable=false)
    private String title;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    @Column(name = "PRICE", nullable=false)
    private double price;


    public Post(){}
}
