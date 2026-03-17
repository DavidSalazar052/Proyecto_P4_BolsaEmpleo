package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
