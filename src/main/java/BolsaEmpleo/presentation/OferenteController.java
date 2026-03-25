package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Oferente;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.OferenteHabilidades;
import BolsaEmpleo.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class OferenteController {

    @Autowired
    private Service service;

    // ──────────────────────────────────────────────
    //  DASHBOARD
    // ──────────────────────────────────────────────

    @GetMapping("/DashboardOferente")
    public String mostrar_DashboardOferente(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "presentation/Oferente/DashboardOferente";
    }

    // ──────────────────────────────────────────────
    //  HABILIDADES DEL OFERENTE
    // ──────────────────────────────────────────────

    /** GET /oferente/habilidades — muestra lista y formulario */
    @GetMapping("/oferente/habilidades")
    public String mostrarHabilidades(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }

        Oferente oferente = service.oferenteByUsuario(usuario.getId());
        List<OferenteHabilidades> habilidades =
                service.findAll_Oferente_hab().stream()
                        .filter(h -> h.getOferente().getId().equals(oferente.getId()))
                        .toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("oferente", oferente);
        model.addAttribute("habilidades", habilidades);
        model.addAttribute("caracteristicas", service.findAll_Caracteristicas());
        return "presentation/Oferente/MisHabilidades";
    }

    /** POST /oferente/habilidades — agrega una habilidad */
    @PostMapping("/oferente/habilidades")
    public String agregarHabilidad(@RequestParam Integer caracteristicaId,
                                   @RequestParam Integer nivel,
                                   HttpSession session,
                                   Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        try {
            Oferente oferente = service.oferenteByUsuario(usuario.getId());
            service.agregarHabilidadOferente(oferente.getId(), caracteristicaId, nivel);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/oferente/habilidades";
    }

    /** POST /oferente/habilidades/eliminar — elimina una habilidad */
    @PostMapping("/oferente/habilidades/eliminar")
    public String eliminarHabilidad(@RequestParam Integer habilidadId,
                                    HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        service.Oferente_hab_Delete(habilidadId);
        return "redirect:/oferente/habilidades";
    }

    // ──────────────────────────────────────────────
    //  CURRÍCULUM (PDF)
    // ──────────────────────────────────────────────

    /** GET /oferente/curriculum — vista para subir CV */
    @GetMapping("/oferente/curriculum")
    public String mostrarSubirCV(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        Oferente oferente = service.oferenteByUsuario(usuario.getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("tieneCv", oferente.getCurriculum() != null);
        return "presentation/Oferente/SubirCV";
    }

    /** POST /oferente/curriculum — recibe y guarda el PDF */
    @PostMapping("/oferente/curriculum")
    public String subirCV(@RequestParam MultipartFile archivo,
                          HttpSession session,
                          Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return "redirect:/login";
        }
        if (archivo.isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Por favor seleccioná un archivo PDF.");
            model.addAttribute("tieneCv", false);
            return "presentation/Oferente/SubirCV";
        }
        try {
            Oferente oferente = service.oferenteByUsuario(usuario.getId());
            oferente.setCurriculum(archivo.getBytes());
            service.OferentesAdd(oferente);
        } catch (IOException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Error al leer el archivo: " + e.getMessage());
            return "presentation/Oferente/SubirCV";
        }
        return "redirect:/oferente/curriculum?ok";
    }

    /** GET /oferente/curriculum/ver — descarga/visualiza el propio CV */
    @GetMapping("/oferente/curriculum/ver")
    public ResponseEntity<byte[]> verMiCV(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"OFE".equals(usuario.getTipo())) {
            return ResponseEntity.status(302).build();
        }
        Oferente oferente = service.oferenteByUsuario(usuario.getId());
        if (oferente.getCurriculum() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"curriculum.pdf\"")
                .body(oferente.getCurriculum());
    }
}