package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OferentesRepository extends JpaRepository<Oferente, String> {
    @Query("select e from Empresa e where e.usuario.id = ?1")
    public Oferente findByUsuarioId(String usuarioId);
    //
    @Query("select e from Empresa e where e.aprobada = true")
    public List<Oferente> findAllByAprobada();

    @Query("select e from Empresa e where e.aprobada = false")
    public List<Oferente> findAllByNoAprobada();

    @Query("select e from Empresa e where e.nombre like %?1%")
    public List<Oferente> findByNombre(String nombre);

}
