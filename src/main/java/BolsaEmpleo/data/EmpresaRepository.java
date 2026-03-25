package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
   //busca por el usuario_id (fk) de Usuario en la tabla
    @Query("select e from Empresa e where e.usuario.id = ?1")
    public Empresa findByUsuarioId(String usuarioId);
    //
    @Query("select e from Empresa e where e.aprobada = true")
    public List<Empresa> findAllByAprobada();

    @Query("select e from Empresa e where e.aprobada = false")
    public List<Empresa> findAllByNoAprobada();

    @Query("select e from Empresa e where e.nombre like %?1%")
    public List<Empresa> findByNombre(String nombre);
}
