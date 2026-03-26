package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private Service service;

    // ══════════════════════════════════════════════════════
    //  GET — Vistas de login y registro
    // ══════════════════════════════════════════════════════

    @GetMapping("/login")
    public String mostrarLogin() {
        return "presentation/Login/viewLogin";
    }

    @GetMapping("/login/empresa")
    public String mostrarRegistroEmpresa() {
        return "presentation/Login/viewRegistroEmpresa";
    }

    @GetMapping("/login/oferente")
    public String mostrarRegistroOferente() {
        return "presentation/Login/viewRegistroOferente";
    }

    // ══════════════════════════════════════════════════════
    //  POST — Login: autentica, verifica aprobación y redirige
    // ══════════════════════════════════════════════════════

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String clave,
            HttpSession session,
            Model model) {

        // 1. Verificar credenciales
        Usuario usuario = service.Usuario_Login(username, clave);
        if (usuario == null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "presentation/Login/viewLogin";
        }

        // 2. Guardar en sesión
        session.setAttribute("usuarioLogueado", usuario);

        // 3. Verificar aprobación según tipo y redirigir
        switch (usuario.getTipo()) {

            case "ADM":
                // Los admins no requieren aprobación
                return "redirect:/DashboardAdministrador";

            case "EMP":
                // Verificamos si la empresa fue aprobada por el admin
                Empresa empresa = service.empresaByUsuario(usuario.getId());
                if (!empresa.isAprobada()) {
                    model.addAttribute("tipo", "EMP");
                    return "presentation/Login/pendienteAprobacion";
                }
                return "redirect:/DashboardEmpresa";

            case "OFE":
                // Verificamos si el oferente fue aprobado por el admin
                Oferente oferente = service.findAll_Oferentes().stream()
                        .filter(o -> o.getId().equals(usuario.getId()))
                        .findFirst()
                        .orElse(null);
                if (oferente == null || !oferente.isAprobado()) {
                    model.addAttribute("tipo", "OFE");
                    return "presentation/Login/pendienteAprobacion";
                }
                return "redirect:/DashboardOferente";

            default:
                model.addAttribute("error", "Tipo de usuario desconocido.");
                return "presentation/Login/viewLogin";
        }
    }

    // ══════════════════════════════════════════════════════
    //  POST — Registro Empresa
    // ══════════════════════════════════════════════════════

    @PostMapping("/registro/empresa")
    public String registrarEmpresa(
            @RequestParam String username,
            @RequestParam String clave,
            @RequestParam String nombre,
            @RequestParam String localizacion,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String descripcion,
            HttpSession session,
            Model model) {
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setClave(clave);
            nuevoUsuario.setTipo("EMP");

            Empresa nuevaEmpresa = new Empresa();
            nuevaEmpresa.setUsuario(nuevoUsuario);
            nuevaEmpresa.setNombre(nombre);
            nuevaEmpresa.setLocalizacion(localizacion);
            nuevaEmpresa.setCorreo(correo);
            nuevaEmpresa.setTelefono(telefono);
            nuevaEmpresa.setDescripcion(descripcion);
            nuevaEmpresa.setAprobada(false);

            service.registrarEmpresa(nuevoUsuario, nuevaEmpresa);

            // Recién registrado → queda pendiente de aprobación
            session.setAttribute("usuarioLogueado", nuevoUsuario);
            model.addAttribute("tipo", "EMP");
            return "presentation/Login/pendienteAprobacion";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/Login/viewRegistroEmpresa";
        }
    }

    // ══════════════════════════════════════════════════════
    //  POST — Registro Oferente
    // ══════════════════════════════════════════════════════

    @PostMapping("/registro/oferente")
    public String registrarOferente(
            @RequestParam String username,
            @RequestParam String clave,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String nacionalidad,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String residencia,
            HttpSession session,
            Model model) {
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setClave(clave);
            nuevoUsuario.setTipo("OFE");

            Oferente nuevoOferente = new Oferente();
            nuevoOferente.setUsuario(nuevoUsuario);
            nuevoOferente.setNombre(nombre);
            nuevoOferente.setApellido(apellido);
            nuevoOferente.setNacionalidad(nacionalidad);
            nuevoOferente.setTelefono(telefono);
            nuevoOferente.setCorreo(correo);
            nuevoOferente.setResidencia(residencia);
            nuevoOferente.setAprobado(false);

            service.registrarOferente(nuevoUsuario, nuevoOferente);

            // Recién registrado → queda pendiente de aprobación
            session.setAttribute("usuarioLogueado", nuevoUsuario);
            model.addAttribute("tipo", "OFE");
            return "presentation/Login/pendienteAprobacion";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/Login/viewRegistroOferente";
        }
    }

    // ══════════════════════════════════════════════════════
    //  GET — Cerrar sesión
    // ══════════════════════════════════════════════════════

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}