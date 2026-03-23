package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private Service service;

    @GetMapping("/DashboardAdministrador")
    public String mostrar_DashboardAdmin(){
        return "presentation/Admin/DashboardAdmin";
    }
    @GetMapping("/EmpresasPendientes")
    public String mostrar_EmpresasPendientes(Model model){
        model.addAttribute("empresasPendientes",service.findAll_EmpresasNoAprobadas());
        return "presentation/Admin/AdminEmpresasPendientes";
    }

    @GetMapping("/OferentesPendientes")
    public String mostrar_OferentesPendientes(Model model){
        model.addAttribute("empresasPendientes",service.findAll_EmpresasNoAprobadas());
        return "presentation/Admin/AdminOferentePendiente";
    }

}
