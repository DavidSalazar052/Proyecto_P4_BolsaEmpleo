package BolsaEmpleo.presentation;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("RootRedirectController")

public class RootRedirectController {
    @GetMapping("/")
    public String redirectRoot(Model model){
        return "redirect:/presentation/ViewPublic";
    }


}
