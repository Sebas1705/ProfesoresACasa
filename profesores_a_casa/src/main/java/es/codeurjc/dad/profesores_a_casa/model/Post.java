package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import java.util.ArrayList;
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
    private User ownerUser;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Contract> contracts=new ArrayList<>();

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Report> reports=new ArrayList<>();

    @OneToOne(cascade=CascadeType.ALL)
    private Ranking ranking;

    @Column(name = "TITLE", nullable=false)
    private String title;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    @Column(name = "PRICE", nullable=false)
    private double price;

    public Post(){}


    public Post(String title, String description, double price){
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public void addContract(Contract contract){
        contracts.add(contract);
        contract.setPost(this);
    }

    public void removeContract(Contract contract){
        contracts.remove(contract);
        contract.setPost(null);
    }

    public void addReport(Report report){
        reports.add(report);
        report.setPost(this);
    }

    public void removeReport(Report report){
        reports.remove(report);
        report.setPost(null);
    }
}
