package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Puesto;
import BolsaEmpleo.logic.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.Color;
import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    // ══════════════════════════════════════════════════════
//  REPORTES
// ══════════════════════════════════════════════════════

    /** GET /admin/reportes — muestra el formulario de selección de mes/año */
    @GetMapping("/admin/reportes")
    public String mostrar_Reportes(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";

        int anioActual = java.time.LocalDate.now().getYear();
        List<Integer> anios = service.aniosDisponibles();
        if (anios.isEmpty()) anios = List.of(anioActual);

        model.addAttribute("anios", anios);
        model.addAttribute("anioActual", anioActual);
        return "presentation/Admin/AdminReportes";
    }

    /** GET /admin/reportes/pdf — genera y descarga el PDF */
    @GetMapping("/admin/reportes/pdf")
    public void generar_ReportePDF(
            @RequestParam int anio,
            @RequestParam int mes,
            HttpSession session,
            HttpServletResponse response) throws IOException {

        if (!esAdmin(session)) {
            response.sendRedirect("/login");
            return;
        }

        List<Puesto> puestos = service.puestosPorMes(anio, mes);
        String nombreMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es", "CR"));
        nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"reporte_puestos_" + anio + "_" + String.format("%02d", mes) + ".pdf\"");

        Document doc = new Document(PageSize.A4, 50, 50, 60, 50);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        // ── Fuentes ─────────────────────────────────────────
        Font fTitulo    = new Font(Font.HELVETICA, 18, Font.BOLD,   new Color(30, 58, 138));
        Font fSubtitulo = new Font(Font.HELVETICA, 11, Font.NORMAL, new Color(100, 116, 139));
        Font fHeader    = new Font(Font.HELVETICA,  9, Font.BOLD,   Color.WHITE);
        Font fCelda     = new Font(Font.HELVETICA,  9, Font.NORMAL, new Color(30, 30, 30));
        Font fResumen   = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(55, 65, 81));
        Font fResumenB  = new Font(Font.HELVETICA, 10, Font.BOLD,   new Color(30, 58, 138));

        // ── Encabezado ──────────────────────────────────────
        Paragraph titulo = new Paragraph("Reporte de Puestos Solicitados", fTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);

        Paragraph subtitulo = new Paragraph("Período: " + nombreMes + " " + anio, fSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(6);
        doc.add(subtitulo);

        LineSeparator linea = new LineSeparator(1f, 100f, new Color(30, 58, 138), Element.ALIGN_CENTER, -2);
        doc.add(new Chunk(linea));

        Paragraph espacio = new Paragraph(" ");
        espacio.setSpacingAfter(4);
        doc.add(espacio);

        // ── Resumen ─────────────────────────────────────────
        long activos   = puestos.stream().filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstado())).count();
        long inactivos = puestos.stream().filter(p -> "INACTIVO".equalsIgnoreCase(p.getEstado())).count();
        long publicos  = puestos.stream().filter(p -> "PUBLICO".equalsIgnoreCase(p.getTipo())).count();
        long privados  = puestos.stream().filter(p -> "PRIVADO".equalsIgnoreCase(p.getTipo())).count();

        PdfPTable resumen = new PdfPTable(4);
        resumen.setWidthPercentage(100);
        resumen.setSpacingAfter(14);

        String[] etiquetas = {"Total de puestos", "Activos", "Inactivos", "Públicos / Privados"};
        String[] valores   = {
                String.valueOf(puestos.size()),
                String.valueOf(activos),
                String.valueOf(inactivos),
                publicos + " / " + privados
        };

        Color bgResumen = new Color(239, 246, 255);
        for (int i = 0; i < 4; i++) {
            PdfPCell cEtiq = new PdfPCell(new Phrase(etiquetas[i], fResumen));
            cEtiq.setBackgroundColor(bgResumen);
            cEtiq.setBorderColor(new Color(191, 219, 254));
            cEtiq.setPadding(6);
            resumen.addCell(cEtiq);

            PdfPCell cVal = new PdfPCell(new Phrase(valores[i], fResumenB));
            cVal.setBackgroundColor(bgResumen);
            cVal.setBorderColor(new Color(191, 219, 254));
            cVal.setPadding(6);
            cVal.setHorizontalAlignment(Element.ALIGN_CENTER);
            resumen.addCell(cVal);
        }
        doc.add(resumen);

        // ── Tabla de puestos ────────────────────────────────
        if (puestos.isEmpty()) {
            Paragraph vacio = new Paragraph(
                    "No se registraron puestos en " + nombreMes + " " + anio + ".",
                    new Font(Font.HELVETICA, 11, Font.ITALIC, new Color(107, 114, 128)));
            vacio.setAlignment(Element.ALIGN_CENTER);
            vacio.setSpacingBefore(20);
            doc.add(vacio);
        } else {
            PdfPTable tabla = new PdfPTable(new float[]{1.5f, 3.5f, 1.5f, 1.5f, 1.5f, 1.5f});
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(4);

            Color bgHeader = new Color(30, 58, 138);
            String[] headers = {"ID", "Descripción", "Empresa", "Tipo", "Estado", "Fecha"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fHeader));
                cell.setBackgroundColor(bgHeader);
                cell.setPadding(7);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(cell);
            }

            Color bgPar   = new Color(248, 250, 252);
            Color bgImpar = Color.WHITE;
            Color border  = new Color(226, 232, 240);

            for (int i = 0; i < puestos.size(); i++) {
                Puesto p = puestos.get(i);
                Color bgFila = (i % 2 == 0) ? bgPar : bgImpar;

                String[] celdas = {
                        String.valueOf(p.getId()),
                        p.getDescripcion() != null ? p.getDescripcion() : "-",
                        p.getEmpresa()     != null ? p.getEmpresa().getNombre() : "-",
                        p.getTipo()        != null ? p.getTipo()    : "-",
                        p.getEstado()      != null ? p.getEstado()  : "-",
                        p.getFecha()       != null ? p.getFecha()   : "-"
                };

                for (String texto : celdas) {
                    PdfPCell cell = new PdfPCell(new Phrase(texto, fCelda));
                    cell.setBackgroundColor(bgFila);
                    cell.setBorderColor(border);
                    cell.setPadding(5);
                    tabla.addCell(cell);
                }
            }
            doc.add(tabla);
        }

        // ── Pie ─────────────────────────────────────────────
        Paragraph pie = new Paragraph(
                "Generado el " + java.time.LocalDate.now() + " · Sistema Bolsa de Empleo",
                new Font(Font.HELVETICA, 8, Font.ITALIC, new Color(156, 163, 175)));
        pie.setAlignment(Element.ALIGN_RIGHT);
        pie.setSpacingBefore(16);
        doc.add(pie);

        doc.close();
    }
}
