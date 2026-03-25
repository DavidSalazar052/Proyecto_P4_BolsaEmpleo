package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Puesto;
import BolsaEmpleo.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmpresaController {

    @Autowired
    private Service service;

    // ── Auxiliar de sesión ────────────────────────────────────────

    private boolean esEmpresa(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return u != null && "EMP".equals(u.getTipo());
    }

    /** Obtiene la Empresa del usuario en sesión. */
    private Empresa getEmpresaSesion(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return service.empresaByUsuario(u.getId());
    }

    // ══════════════════════════════════════════════════════════════
    //  DASHBOARD EMPRESA
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/DashboardEmpresa")
    public String mostrar_DashboardEmpresa(HttpSession session, Model model) {
        if (!esEmpresa(session)) return "redirect:/login";
        Empresa empresa = getEmpresaSesion(session);
        model.addAttribute("empresa", empresa);
        return "presentation/Empresa/DashboardEmpresa";
    }

    // ══════════════════════════════════════════════════════════════
    //  EDITAR DATOS DE LA EMPRESA
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/editar")
    public String mostrar_EditarEmpresa(HttpSession session, Model model) {
        if (!esEmpresa(session)) return "redirect:/login";
        model.addAttribute("empresa", getEmpresaSesion(session));
        return "presentation/Empresa/EditarEmpresa";
    }

    @PostMapping("/empresa/editar")
    public String guardar_EditarEmpresa(
            @RequestParam String nombre,
            @RequestParam String localizacion,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String descripcion,
            HttpSession session, Model model) {

        if (!esEmpresa(session)) return "redirect:/login";
        try {
            Empresa empresa = getEmpresaSesion(session);
            empresa.setNombre(nombre);
            empresa.setLocalizacion(localizacion);
            empresa.setCorreo(correo);
            empresa.setTelefono(telefono);
            empresa.setDescripcion(descripcion);
            service.empresaUpdate(empresa);
            return "redirect:/DashboardEmpresa?exito=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            model.addAttribute("empresa", getEmpresaSesion(session));
            return "presentation/Empresa/EditarEmpresa";
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  MIS PUESTOS
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/puestos")
    public String mostrar_MisPuestos(HttpSession session, Model model) {
        if (!esEmpresa(session)) return "redirect:/login";
        Empresa empresa = getEmpresaSesion(session);
        List<Puesto> puestos = service.puestosByEmpresa(empresa.getId());
        model.addAttribute("empresa", empresa);
        model.addAttribute("puestos", puestos);
        return "presentation/Empresa/MisPuestos";
    }

    // ══════════════════════════════════════════════════════════════
    //  CREAR PUESTO — formulario
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/puestos/nuevo")
    public String mostrar_NuevoPuesto(HttpSession session, Model model) {
        if (!esEmpresa(session)) return "redirect:/login";
        // Pasamos todas las características para el selector de habilidades
        model.addAttribute("caracteristicas", service.findAll_Caracteristicas());
        return "presentation/Empresa/NuevoPuesto";
    }

    @PostMapping("/empresa/puestos/nuevo")
    public String crear_Puesto(
            @RequestParam String descripcion,
            @RequestParam Integer salario,
            @RequestParam String tipo,
            HttpSession session, Model model) {

        if (!esEmpresa(session)) return "redirect:/login";
        try {
            Empresa empresa = getEmpresaSesion(session);
            service.crearPuesto(empresa, descripcion, salario, tipo);
            return "redirect:/empresa/puestos?exito=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el puesto: " + e.getMessage());
            model.addAttribute("caracteristicas", service.findAll_Caracteristicas());
            return "presentation/Empresa/NuevoPuesto";
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  AGREGAR CARACTERÍSTICA A UN PUESTO
    // ══════════════════════════════════════════════════════════════

    @PostMapping("/empresa/puestos/habilidad")
    public String agregar_HabilidadPuesto(
            @RequestParam Integer puestoId,
            @RequestParam Integer caracteristicaId,
            @RequestParam Integer nivel,
            HttpSession session) {

        if (!esEmpresa(session)) return "redirect:/login";
        service.agregarHabilidadPuesto(puestoId, caracteristicaId, nivel);
        return "redirect:/empresa/puestos/" + puestoId;
    }

    // ══════════════════════════════════════════════════════════════
    //  VER DETALLE DE UN PUESTO (habilidades requeridas)
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/puestos/{id}")
    public String mostrar_DetallePuesto(
            @org.springframework.web.bind.annotation.PathVariable Integer id,
            HttpSession session, Model model) {

        if (!esEmpresa(session)) return "redirect:/login";
        Puesto puesto = service.PuestoRead(id);
        model.addAttribute("puesto", puesto);
        model.addAttribute("habilidades", service.habilidadesByPuesto(id));
        model.addAttribute("caracteristicas", service.findAll_Caracteristicas());
        return "presentation/Empresa/DetallePuesto";
    }

    // ══════════════════════════════════════════════════════════════
    //  DESACTIVAR PUESTO
    // ══════════════════════════════════════════════════════════════

    @PostMapping("/empresa/puestos/desactivar")
    public String desactivar_Puesto(
            @RequestParam Integer puestoId,
            HttpSession session) {

        if (!esEmpresa(session)) return "redirect:/login";
        service.desactivarPuesto(puestoId);
        return "redirect:/empresa/puestos";
    }

    // ══════════════════════════════════════════════════════════════
    //  BUSCAR CANDIDATOS PARA UN PUESTO
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/candidatos")
    public String mostrar_Candidatos(
            @RequestParam Integer puestoId,
            HttpSession session, Model model) {

        if (!esEmpresa(session)) return "redirect:/login";
        Puesto puesto = service.PuestoRead(puestoId);
        List<Service.ResultadoCandidato> candidatos = service.calcularCandidatos(puestoId);
        model.addAttribute("puesto", puesto);
        model.addAttribute("candidatos", candidatos);
        return "presentation/Empresa/Candidatos";
    }

    // ══════════════════════════════════════════════════════════════
    //  VER DETALLE DE UN OFERENTE (desde búsqueda de candidatos)
    // ══════════════════════════════════════════════════════════════

    @GetMapping("/empresa/candidatos/detalle")
    public String mostrar_DetalleOferente(
            @RequestParam Integer oferenteId,
            HttpSession session, Model model) {

        if (!esEmpresa(session)) return "redirect:/login";
        Oferente oferente = service.findAll_Oferentes().stream()
                .filter(o -> o.getId().equals(oferenteId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado"));
        model.addAttribute("oferente", oferente);
        return "presentation/Empresa/DetalleOferente";
    }
}