package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private Service service;

    @GetMapping("/DashboardAdministrador")
    public String mostrar_DashboardAdmin(){
        return "presentation/Admin/DashboardAdmin";
    }

    @GetMapping("/AdminCaracteristicas")
    public String mostrar_CrearCaracteristicas(){
        return "presentation/Admin/AdminCaracteristicas";
    }

    @GetMapping("/EmpresasPendientes")
    public String mostrar_EmpresasPendientes(Model model){
        model.addAttribute("empresasPendientes",service.findAll_EmpresasNoAprobadas());
        return "presentation/Admin/AdminEmpresasPendientes";
    }

    @GetMapping("/OferentesPendientes")
    public String mostrar_OferentesPendientes(Model model){
        model.addAttribute("oferentesPendientes",service.findAll_Oferentes_NoAprobadas());
        return "presentation/Admin/AdminOferentePendiente";
    }

    @PostMapping("/admin/empresas/aprobar")
    public String aprobarEmpresa(@RequestParam Integer id) {
        service.aprobarEmpresa(id);
        return "redirect:/EmpresasPendientes";  // redirige para refrescar la lista
    }
    @PostMapping("/admin/oferentes/aprobar")
    public String aprobarOferente(@RequestParam Integer id) {
        service.aprobarOferente(id);
        return "redirect:/OferentesPendientes";
    }

}
