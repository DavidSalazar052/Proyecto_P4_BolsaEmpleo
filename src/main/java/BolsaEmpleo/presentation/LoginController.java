package BolsaEmpleo.presentation;

import BolsaEmpleo.data.EmpresaRepository;
import BolsaEmpleo.data.OferentesRepository;
import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import BolsaEmpleo.logic.Base.Usuario;
import BolsaEmpleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  LoginController                                              ║
 * ║                                                               ║
 * ║  Con Spring Security el flujo cambia:                         ║
 * ║  • GET  /login        → este controller muestra el formulario ║
 * ║  • POST /login        → lo intercepta Spring Security,        ║
 * ║                         NO llega a este controller            ║
 * ║  • GET  /logout       → lo intercepta Spring Security         ║
 * ║                                                               ║
 * ║  Este controller solo maneja:                                 ║
 * ║  • Mostrar las vistas de login / registro                     ║
 * ║  • Procesar los formularios de registro (empresa y oferente)  ║
 * ║  • Mostrar la pantalla de pendiente de aprobación             ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
@Controller
public class LoginController {

    @Autowired private Service service;
    @Autowired private EmpresaRepository  empresaRepo;
    @Autowired private OferentesRepository oferenteRepo;

    // ══════════════════════════════════════════════════════
    //  GET — Formulario de login
    //  Spring Security redirige aquí cuando un recurso
    //  protegido es accedido sin autenticación.
    // ══════════════════════════════════════════════════════

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        return "presentation/Login/viewLogin";
    }

    // ══════════════════════════════════════════════════════
    //  GET — Vistas de registro
    // ══════════════════════════════════════════════════════

    @GetMapping("/login/empresa")
    public String mostrarRegistroEmpresa() {
        return "presentation/Login/viewRegistroEmpresa";
    }

    @GetMapping("/login/oferente")
    public String mostrarRegistroOferente() {
        return "presentation/Login/viewRegistroOferente";
    }

    // ══════════════════════════════════════════════════════
    //  POST — Registro Empresa
    //  Crea usuario + empresa con aprobada=false.
    //  Muestra pantalla de pendiente (no hace login automático).
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

            model.addAttribute("tipo", "OFE");
            return "presentation/Login/pendienteAprobacion";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/Login/viewRegistroOferente";
        }
    }
}