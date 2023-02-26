package es.codeurjc.dad.profesores_a_casa.service;

import java.util.*;

import javax.servlet.http.HttpSession;

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
    
    //Home services:
    public void setUpOfPosts(Model model,Pageable pageable,String sortBy,boolean desc){
        Page<Post> post;
        if(sortBy!=null){
            post=posts.getPage(PageRequest.of(pageable.getPageNumber(),sizePage,(desc)?Sort.by(sortBy).descending():Sort.by(sortBy).ascending()));
        }else{
            post=posts.getPage(PageRequest.of(pageable.getPageNumber(),sizePage));
        }
        model.addAttribute("isPerfil",false);
        model.addAttribute("posts",post);
        model.addAttribute("hasPrev",post.hasPrevious());
        model.addAttribute("hasNext",post.hasNext());	
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

    //LogIn services:
    public String getLogPage(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "LogIn";
    }
    public String makeLogIn(Model model,HttpSession session,String logname,String password){
        Optional<User> u=users.findUser(logname);
        if(u.isPresent()&&u.get().getPassword().equals(password)){
            session.setAttribute("User",u.get());
            model.addAttribute("User", u.get());
            setUpOfPosts(model,PageRequest.of(0,10),null,false);
            return "Home";
        }
        model.addAttribute("Incorrect",true);
        model.addAttribute("error","No existe el usuario o la contraseña no es correcta");
        return "LogIn";
    }
    public String makeLogOut(Model model,HttpSession session){
        session.invalidate();
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //SignUp services:
    public String getSignUpPage(Model model){
        model.addAttribute("Incorrect", false);
        model.addAttribute("error",null);
        return "SignUp";
    }
    public String makeSignUp(Model model,HttpSession session,String logname,String password,String email){
        Optional<User> u=users.findUser(logname);
        if(u.isPresent()){
            model.addAttribute("Incorrect",true);
            model.addAttribute("error","El nombre ya esta en uso");
            return "SignUp";
        }
        User newUser=new User(logname,password,email);
        users.save(newUser);
        session.setAttribute("User",newUser);
        model.addAttribute("User",newUser);
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Profiles services:
    public String setUpMiPerfil(Model model,HttpSession session){
        User user = (User) session.getAttribute("User");
        if(user!=null){
            List<Post> lPosts=posts.findPosts(user);
            List<Contract> cT=contracts.findContractAsTeacher(user);
            List<Contract> cS=contracts.findContractAsStudent(user);
            model.addAttribute("posts",lPosts);
            model.addAttribute("cT",cT);
            model.addAttribute("cS",cS);
            model.addAttribute("User", user);
            model.addAttribute("isPerfil",true);
            return "MyProfile";
        }
        session.invalidate();
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String setUpPerfil(Model model,HttpSession session,long userId){
        User user=(User)session.getAttribute("User");
        model.addAttribute("User",user);
        Optional<User> userShow=users.findUser(userId);
        model.addAttribute("User", user);
        if(userShow.isPresent()){
            User u=userShow.get();
            List<Post> lPosts=posts.findPosts(u);
            model.addAttribute("posts",lPosts);
            model.addAttribute("UserShow",u);
            return "OtherProfile";
        }
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String deleteUser(Model model,HttpSession session){
        User u=(User)session.getAttribute("User");
        users.delete(u.getId());
        session.invalidate();
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String getChangePage(Model model,HttpSession session){
        User u=(User)session.getAttribute("User");
        model.addAttribute("User",u);
        return "EditUserProfile";
    }
    public String changeProfile(Model model,HttpSession session,String logname,String selfDescription){
        User u=(User)session.getAttribute("User");
        if(!users.findUser(logname).isPresent())u.setLogname(logname);
        u.setSelfDescription(selfDescription);
        users.save(u);
        session.setAttribute("User",u);
        return setUpMiPerfil(model,session); 
    }

    //Posts services:
    public String showPost(Model model,HttpSession session,long postId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage",post.get().getRanking().getAverage());
            return "Post";
        }
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String getPostPage(Model model,HttpSession session){
        User u=(User)session.getAttribute("User");
        model.addAttribute("User",u);
        return "NewPost";
    }
    public String makeNewPost(Model model,HttpSession session,String title,String description,double price){
        User u=(User) session.getAttribute("User");
        Post post = new Post(title,description,price);
        post.setRanking(new Ranking(1,1));
        posts.save(post);
        u.addPost(post);
        users.save(u);
        posts.save(post);
        session.setAttribute("User", u);
        return setUpMiPerfil(model, session);
    }
    public String rankPost(Model model,HttpSession session,long postId,int punt){
        User u=(User)session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            Ranking r = post.get().getRanking();
            r.incrementScore(punt);
            post.get().setRanking(r);
            posts.save(post.get());
            model.addAttribute("Post", post.get());
            int avg=post.get().getRanking().getAverage();
            List<Integer> stars=new ArrayList<Integer>();
            for(int i=1;i<=avg&&i<=5;i++)stars.add(i);
            model.addAttribute("Stars",stars);
            return "Post";
        }
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String deletePost(Model model,HttpSession session,long postId){
        User u=(User)session.getAttribute("User");
        Optional<Post> p=posts.findPost(postId);
        Post post=p.get();
        if(posts.exists(post)){
            u=post.getOwnerUser();
            u.removePost(post);
            posts.deletePost(post.getId());
            users.save(u);
        } 
        session.setAttribute("User",u);
        model.addAttribute("User",u);
        return setUpMiPerfil(model,session);
    }

    //Reports services:
    public String getReportPage(Model model,HttpSession session,long postId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        session.setAttribute("Post",postId);
        return "NewReport";
    }
    public String makeNewReport(Model model,HttpSession session,String motive,String description){
        User u=(User) session.getAttribute("User");
        Optional<Post> post=posts.findPost((long)session.getAttribute("Post"));
        if(post.isPresent()){
            Post p=post.get();
            Report report = new Report(motive, description);
            report.setAuthor(u);
            p.addReport(report);
            users.save(u);
            posts.save(p);
            reports.save(report);
        }
        model.addAttribute("User",u);
        session.setAttribute("Post",null);
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Contract services:
    public String getContractPage(Model model,HttpSession session,long postId,long studentId,long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> s=users.findUser(studentId),t;
        Optional<Post> p;
        if(s.isPresent()){
            t=users.findUser(teacherId);
            if(t.isPresent()){
                p=posts.findPost(postId);
                if(p.isPresent()){
                    model.addAttribute("Post",p.get());
                    model.addAttribute("Teacher",t.get());
                    model.addAttribute("Student",s.get());
                    return "NewContract";
                }
            }
        }
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String makeNewContract(Model model,HttpSession session,long postId,long studentId,long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> s=users.findUser(studentId),t;
        Optional<Post> p;
        if(s.isPresent()){
            t=users.findUser(teacherId);
            if(t.isPresent()){
                p=posts.findPost(postId);
                if(p.isPresent()){
                    Contract contract=new Contract();
                    contracts.save(contract);
                    s.get().addContractAsStudent(contract);
                    t.get().addContractAsTeacher(contract);
                    p.get().addContract(contract);
                    users.save(s.get());
                    users.save(t.get());
                    posts.save(p.get());
                    contracts.save(contract); 
                    setUpMiPerfil(model,session);
                }
            } 
        }
        setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    public String deleteContract(Model model,HttpSession session,long contractId,boolean teacher){
        Optional<Contract> c=contracts.findContract(contractId);
        if(c.isPresent()){
            Contract contract=c.get();
            if(teacher)contract.setTeacherWantToDelete(!contract.isTeacherWantToDelete());
            else contract.setStudentWantToDelete(!contract.isStudentWantToDelete());
            contracts.save(contract);
            if(contract.isTeacherWantToDelete()&&contract.isStudentWantToDelete())contracts.delete(contractId);
        }
        setUpMiPerfil(model,session);
        return "MyProfile";
    }
}
