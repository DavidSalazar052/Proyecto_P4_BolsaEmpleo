package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuestoRepository extends JpaRepository<Puesto,Integer>  {
    @Query(value = "SELECT * FROM puesto WHERE tipo = 'publico' LIMIT 5", nativeQuery = true)
    public List<Puesto> findTop5Puestos();

}
