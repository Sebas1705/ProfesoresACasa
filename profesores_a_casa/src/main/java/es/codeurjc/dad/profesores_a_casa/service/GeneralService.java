package es.codeurjc.dad.profesores_a_casa.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import es.codeurjc.dad.profesores_a_casa.model.*;

@Service
public class GeneralService {
    
    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private ContractService contracts;
    @Autowired private ReportService reports;

    private static final int sizePage = 10;


    //Casos 1:1->
    //  Bidireccional:
    //      Sin cascade: se guarda cada uno por separado y se hace el set
    //      Con cascade: se guarda solo el principal tras setear el secundario
    //Casos N:1->
    //  Bidireccional:
    //      Sin cascade: se guarda la entidad secundaria, se setea a la principal y se guarda la principal
    //      Con cascade: se hace funcion de add en la secundaria que fue mapeada

    public void autoInitDBTest(int n){
        for (int i=0;i<n;i++){
            User student=new User("ExampleLogname_0_"+i,"123","ExampleEmail_0_"+i);
            User teacher=new User("ExampleLogname_1_"+i,"ExamplePassword_1_"+i,"ExampleEmail_1_"+i);
            users.save(student);
            users.save(teacher);
            Post post=new Post("ExampleTitle_"+i,"ExampleDescription_"+i,(Math.random()*50));
            post.setRanking(new Ranking(Math.random()*i,i));
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

    public void setUpOfPosts(Model model,Pageable pageable,String sortBy,boolean desc){
        Page<Post> post;
        if(sortBy!=null){
            post=posts.getPage(PageRequest.of(pageable.getPageNumber(),sizePage,(desc)?Sort.by(sortBy).descending():Sort.by(sortBy).ascending()));
        }else{
            post=posts.getPage(PageRequest.of(pageable.getPageNumber(),sizePage));
        }
        model.addAttribute("posts", post);
        model.addAttribute("hasPrev", post.hasPrevious());
        model.addAttribute("hasNext", post.hasNext());	
        model.addAttribute("prevPage",post.getNumber()-1);
        model.addAttribute("nextPage",post.getNumber()+1);	
        int totalpages=post.getTotalPages();
        List<Integer> indexNext=new ArrayList<Integer>();
        List<Integer> indexPrev=new ArrayList<Integer>();
        for(int i=post.getNumber()+1;i<totalpages;i++)indexNext.add(i);
        for(int i=0;i<post.getNumber();i++)indexPrev.add(i);
        model.addAttribute("actualPage",post.getNumber());
        model.addAttribute("prevPages",indexPrev);
        model.addAttribute("nextPages",indexNext);
    }
}
