package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuestoRepository extends JpaRepository<Puesto,String>  {
    @Query(value = "SELECT * FROM puesto WHERE tipo = 'publico' LIMIT 5", nativeQuery = true)
    public List<Puesto> findTop5Puestos();

//    // Puestos públicos que tengan AL MENOS UNA de las características seleccionadas
//    @Query("SELECT DISTINCT p FROM PuestoHabilidades ph " +
//            "JOIN ph.puesto p " +
//            "WHERE ph.habilidad.id IN :ids " +
//            "AND p.tipo = 'PUBLICO' " +
//            "AND p.estado = 'ACTIVO'")
//    public List<Puesto> findPuestosPublicosByCaracteristicas(@Param("ids") List<String> ids);
//

}
