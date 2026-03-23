package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OferenteController {

    private Service service;

    @GetMapping("/DashboardOferente")
    public String mostrar_DashboardOferente() {
        return "presentation/Oferente/DashboardOferente";
    }
}