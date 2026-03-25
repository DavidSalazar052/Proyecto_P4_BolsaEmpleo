package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private Service service;

    // ══════════════════════════════════════════════════════
    //  Verificación de sesión (método auxiliar privado)
    // ══════════════════════════════════════════════════════

    private boolean esAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return u != null && "ADM".equals(u.getTipo());
    }

    // ══════════════════════════════════════════════════════
    //  Dashboard Admin
    // ══════════════════════════════════════════════════════

    @GetMapping("/DashboardAdministrador")
    public String mostrar_DashboardAdmin(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "presentation/Admin/DashboardAdmin";
    }

    // ══════════════════════════════════════════════════════
    //  Empresas pendientes
    // ══════════════════════════════════════════════════════

    @GetMapping("/EmpresasPendientes")
    public String mostrar_EmpresasPendientes(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        model.addAttribute("empresasPendientes", service.findAll_EmpresasNoAprobadas());
        return "presentation/Admin/AdminEmpresasPendientes";
    }

    @PostMapping("/admin/empresas/aprobar")
    public String aprobarEmpresa(@RequestParam Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        service.aprobarEmpresa(id);
        return "redirect:/EmpresasPendientes";
    }

    // ══════════════════════════════════════════════════════
    //  Oferentes pendientes
    // ══════════════════════════════════════════════════════

    @GetMapping("/OferentesPendientes")
    public String mostrar_OferentesPendientes(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        model.addAttribute("oferentesPendientes", service.findAll_Oferentes_NoAprobadas());
        return "presentation/Admin/AdminOferentePendiente";
    }

    @PostMapping("/admin/oferentes/aprobar")
    public String aprobarOferente(@RequestParam Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        service.aprobarOferente(id);
        return "redirect:/OferentesPendientes";
    }

    // ══════════════════════════════════════════════════════
    //  Características — GET: listar + cargar formulario
    // ══════════════════════════════════════════════════════

    @GetMapping("/AdminCaracteristicas")
    public String mostrar_Caracteristicas(
            @RequestParam(required = false) String exito,
            @RequestParam(required = false) String error,
            HttpSession session,
            Model model) {

        if (!esAdmin(session)) return "redirect:/login";

        // Padres (raíces sin padre) para mostrar en la lista jerárquica
        List<Caracteristicas> padres = service.findPadresCaracteristicas();

        // Mapa padreId → hijos directos, usado en Thymeleaf como hijos[padre.id]
        Map<Integer, List<Caracteristicas>> hijos = new HashMap<>();
        for (Caracteristicas padre : padres) {
            hijos.put(padre.getId(), service.findHijosCaracteristicas(padre.getId()));
        }

        // Todas las características para el <select> del formulario "Padre"
        List<Caracteristicas> todas = service.findAll_Caracteristicas();

        model.addAttribute("padres", padres);
        model.addAttribute("hijos", hijos);
        model.addAttribute("todas", todas);

        // Mensajes de feedback tras redirect
        if (exito != null) model.addAttribute("exito", true);
        if (error  != null) model.addAttribute("error", error);

        return "presentation/Admin/AdminCaracteristicas";
    }

    // ══════════════════════════════════════════════════════
    //  Características — POST: crear y guardar en BD
    // ══════════════════════════════════════════════════════

    @PostMapping("/admin/caracteristicas/crear")
    public String crearCaracteristica(
            @RequestParam String nombre,
            @RequestParam(required = false) Integer padreId,
            HttpSession session) {

        if (!esAdmin(session)) return "redirect:/login";

        try {
            service.crearCaracteristica(nombre, padreId);
            return "redirect:/AdminCaracteristicas?exito=true";
        } catch (Exception e) {
            return "redirect:/AdminCaracteristicas?error=" + e.getMessage();
        }
    }
}
