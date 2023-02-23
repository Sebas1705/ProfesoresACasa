package es.codeurjc.dad.profesores_a_casa.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch=FetchType.EAGER,mappedBy="ownerUser", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Post> posts=new ArrayList<Post>();

    @OneToMany(mappedBy="author", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Report> reports=new ArrayList<Report>();

    @OneToMany(mappedBy="teacher", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Contract> contractsAsTeacher=new ArrayList<Contract>();

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Contract> contractsAsStudent=new ArrayList<Contract>();

    @Column(name = "LOGNAME")
    private String logname;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SELF_DESCRIPTION")
    private String selfDescription;

    public User(){this(null,null,null);}

    public User(String logname,String password,String email){
        this.logname=logname;
        this.password=password;
        this.selfDescription=null;
        this.email=email;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setOwnerUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setOwnerUser(null);
    }

    public void addReport(Report report){
        reports.add(report);
        report.setAuthor(this);
    }

    public void removeReport(Report report){
        reports.remove(report);
        report.setAuthor(null);
    }

    public void addContractAsTeacher(Contract contract){
        contractsAsTeacher.add(contract);
        contract.setTeacher(this);
    }

    public void removeContractAsTeacher(Contract contract){
        contractsAsTeacher.remove(contract);
        contract.setTeacher(null);
    }

    public void addContractAsStudent(Contract contract){
        contractsAsStudent.add(contract);
        contract.setStudent(this);
    }

    public void removeContractAsStudent(Contract contract){
        contractsAsStudent.remove(contract);
        contract.setStudent(null);
    }

}
