package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpresaController {
    @Autowired
    private Service service;

    @GetMapping("/DashboardEmpresa")
    public String mostrar_DashboardEmpresa(){
        return "presentation/Empresa/DashboardEmpresa";
    }

}
