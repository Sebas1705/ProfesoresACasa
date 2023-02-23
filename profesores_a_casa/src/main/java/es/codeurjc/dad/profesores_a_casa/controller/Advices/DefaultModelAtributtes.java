package es.codeurjc.dad.profesores_a_casa.controller.Advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultModelAtributtes {

    @ModelAttribute("descriptionIntro")
    public String textoHome(){
        return "ProfesoresACasa es una aplicación wed dirigida a la comuniciación "+
        "entre profesores particulares, que quieran dar clases a domicilio, y clientes. "+
        "Para ello a lo largo de la app podra encontrar varias ofertas hechas por los propios profesores "+
        "que tienen cuenta en la web, tu como cliente puedes explorar y elegir por estas ofertas o simplemente "+
        "volverte profesor y publicar tus propias ofertas. Para contratar y ofertar tienes que tener una cuenta, por "+
        "lo que creala en registro o incia sesión si ya tienes una!";
    }

    @ModelAttribute("Bienvenido")
    public String getBienvenido(){return "~~Bienvenido!!~~";}

    @ModelAttribute("BienvenidoDeVuelta")
    public String getBienevenidoDeVuelta() {return "Bienvenido de vuelta!!";}

}
