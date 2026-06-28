package es.codeurjc.dad.profesores_a_casa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.codeurjc.dad.profesores_a_casa.model.*;

@Service
public class DatabaseInitializer {
    
    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private ContractService contracts;
    @Autowired private ReportService reports;
    @Autowired private PasswordEncoder passwordEncoder;


    // 1:1 relationships:
    //   Bidirectional without cascade: save each entity separately, then call the setter
    //   Bidirectional with cascade: save only the owning side after setting the child
    // N:1 relationships:
    //   Bidirectional without cascade: save the child entity, assign it to the parent, then save the parent
    //   Bidirectional with cascade: call the add helper on the child side (which is the mapped side)

    public void autoInitDBTest(int n){
        for (int i=0;i<n;i++){
            User student=new User("ExampleLogname_0_"+i,passwordEncoder.encode("123"),"ExampleEmail_0_"+i);
            User teacher=new User("ExampleLogname_1_"+i,passwordEncoder.encode("ExamplePassword_1_"+i),"ExampleEmail_1_"+i);
            users.save(student);
            users.save(teacher);
            Post post=new Post("ExampleTitle_"+i,"ExampleDescription_"+i,(double)Math.round((Math.random()*101)*100d)/100d);
            post.setRanking(new Ranking((int)(Math.random()*6),1));
            posts.save(post);
            Report report=new Report("ExampleMotive_"+i,"ExampleDescription_"+i);
            student.addReport(report);
            post.addReport(report);
            teacher.addPost(post);
            reports.save(report);
            Contract contract=new Contract();
            contracts.save(contract);
            student.addContractAsStudent(contract);
            teacher.addContractAsTeacher(contract);
            post.addContract(contract);
            users.save(student);
            users.save(teacher);
            posts.save(post);
            reports.save(report);
            contracts.save(contract);    
        }
    }
}
