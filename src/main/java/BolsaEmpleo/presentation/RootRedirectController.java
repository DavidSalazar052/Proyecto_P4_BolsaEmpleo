package BolsaEmpleo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {
    @GetMapping("/")
    public String redirectRoot(Model model){
        return "presentation/viewpublic"; // el redirect se usa para mandarlo al controller que lo dirige al html, pero se puede hacer de una vez (Alex)
    }
    @GetMapping("/buscaPuesto")
    public String mostrarBuscaPuesto(Model model){
        return "presentation/buscaPuesto";
    }
}
