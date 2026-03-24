package BolsaEmpleo.presentation;

import BolsaEmpleo.logic.Base.Empresa;
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
    public String mostrarLogin(){
        return "presentation/login/viewLogin";
    }

    @GetMapping("/login/empresa")
    public String mostrarRegistroEmpresa(){
        return "presentation/login/viewRegistroEmpresa";
    }

    @GetMapping("/login/oferente")
    public String mostrarRegistroOferente(){
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
            // 1. UUID como String — la BD usa varchar(255) como PK, no autoincrement
            String nuevoId = UUID.randomUUID().toString();

            // 2. Creamos el Usuario con el ID ya puesto
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setId(nuevoId);
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setClave(clave);
            nuevoUsuario.setTipo("EMP");

            // 3. Creamos la Empresa — @MapsId toma el ID del Usuario automáticamente
            //    NO hace falta empresa.setId(nuevoId)
            Empresa nuevaEmpresa = new Empresa();
            nuevaEmpresa.setUsuario(nuevoUsuario);   // esto es suficiente para el ID
            nuevaEmpresa.setNombre(nombre);
            nuevaEmpresa.setLocalizacion(localizacion);
            nuevaEmpresa.setCorreo(correo);
            nuevaEmpresa.setTelefono(telefono);
            nuevaEmpresa.setDescripcion(descripcion);
            nuevaEmpresa.setAprobada(false);

            // 4. Service guarda Usuario primero, luego Empresa
            service.registrarEmpresa(nuevoUsuario, nuevaEmpresa);

            model.addAttribute("registroExito", true);
            return "presentation/login/viewRegistroEmpresa";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "presentation/login/viewRegistroEmpresa";
        }
    }

}
