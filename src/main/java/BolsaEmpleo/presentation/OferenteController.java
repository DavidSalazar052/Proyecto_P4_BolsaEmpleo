package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OferenteController {

    @Autowired
    private Service service;

    @GetMapping("/DashboardOferente")
    public String mostrar_DashboardOferente(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "presentation/Oferente/DashboardOferente";
    }
}