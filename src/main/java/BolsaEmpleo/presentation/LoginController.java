package BolsaEmpleo.presentation;

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

import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private Service service;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "presentation/login/viewLogin";
    }

    @GetMapping("/login/empresa")
    public String mostrarRegistroEmpresa() {
        return "presentation/login/viewRegistroEmpresa";
    }

    @GetMapping("/login/oferente")
    public String mostrarRegistroOferente() {
        return "presentation/login/viewRegistroOferente";
    }


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

            model.addAttribute("registroExito", true);
            return "presentation/login/viewRegistroEmpresa";

        } catch (Exception e){
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/login/viewRegistroEmpresa";
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
            // Usuario — el ID lo asigna la BD con IDENTITY (autoincrement)
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setClave(clave);
            nuevoUsuario.setTipo("OFE");

            // Oferente — @MapsId toma el ID del Usuario una vez guardado
            Oferente nuevoOferente = new Oferente();
            nuevoOferente.setUsuario(nuevoUsuario);
            nuevoOferente.setNombre(nombre);
            nuevoOferente.setApellido(apellido);
            nuevoOferente.setNacionalidad(nacionalidad);
            nuevoOferente.setTelefono(telefono);
            nuevoOferente.setCorreo(correo);
            nuevoOferente.setResidencia(residencia);
            nuevoOferente.setAprobado(false);

            // Service guarda Usuario primero, luego Oferente
            service.registrarOferente(nuevoUsuario, nuevoOferente);

            model.addAttribute("registroExito", true);
            return "presentation/Login/viewRegistroOferente";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/Login/viewRegistroOferente";
        }
    }
}
