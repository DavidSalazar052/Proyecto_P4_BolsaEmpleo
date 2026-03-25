package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RootRedirectController {

    @Autowired
    private Service service;

    // ══════════════════════════════════════════════════════
    //  Página pública — Top 5 puestos recientes
    // ══════════════════════════════════════════════════════

    @GetMapping("/")
    public String mostrarPublico(Model model) {
        List<Puesto> puestos = service.Top5_PuestosRecientes();
        model.addAttribute("puestos", puestos);
        return "presentation/viewpublic";
    }

    // ══════════════════════════════════════════════════════
    //  Buscar puestos por características
    //  GET /buscaPuesto              → solo carga el formulario
    //  GET /buscaPuesto?ids=1&ids=3  → busca y muestra resultados
    // ══════════════════════════════════════════════════════

    @GetMapping("/buscaPuesto")
    public String mostrarBuscaPuesto(
            @RequestParam(required = false) List<Integer> ids,
            Model model) {

        // Árbol de características para los checkboxes
        List<Caracteristicas> padres = service.findPadresCaracteristicas();
        Map<Integer, List<Caracteristicas>> hijos = new HashMap<>();
        for (Caracteristicas padre : padres) {
            hijos.put(padre.getId(), service.findHijosCaracteristicas(padre.getId()));
        }
        model.addAttribute("padres", padres);
        model.addAttribute("hijos", hijos);

        // Si vinieron ids → ejecutamos la búsqueda; si no, puestos queda null
        if (ids != null && !ids.isEmpty()) {
            List<Puesto> resultados = service.buscarPuestosPorCaracteristicas(ids);
            model.addAttribute("puestos", resultados);
        }
        // puestos == null en el model → la vista muestra "Seleccioná características"

        return "presentation/buscaPuesto";
    }
}