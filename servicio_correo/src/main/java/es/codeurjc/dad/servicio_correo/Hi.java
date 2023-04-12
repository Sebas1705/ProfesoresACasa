package es.codeurjc.dad.servicio_correo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hi {
    
    @GetMapping("/")
    public String hello(){return "Hello I'm running...";}
}
