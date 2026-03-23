package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private Service service;

    @GetMapping("/login")
    public String mostrarLogin(){
        return "presentation/login/viewLogin";
    }

    @GetMapping("/login/empresa")
    public String mostrarRegistroEmpresa(){
        return "presentation/login/viewRegistroEmpresa";
    }

    @GetMapping("/login/oferente")
    public String mostrarRegistroOferente(){
        return "presentation/login/viewRegistroOferente";
    }


}
