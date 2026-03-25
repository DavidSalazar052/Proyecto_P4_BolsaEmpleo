package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import BolsaEmpleo.logic.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RootRedirectController {
    @Autowired
    private Service service;

    @GetMapping("/")
    public String redirectRoot(){
        return "presentation/viewpublic"; // el redirect se usa para mandarlo al controller que lo dirige al html, pero se puede hacer de una vez (Alex)
    }

    @GetMapping("/buscaPuesto")
    public String mostrarBuscaPuesto(Model model){
        return "presentation/buscaPuesto";
    }


}
