package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PuestoRepository extends JpaRepository<Puesto,String>  {
    @Query(value = "SELECT * FROM puesto WHERE tipo = 'publico' LIMIT 5", nativeQuery = true)
    public List<Puesto> findTop5Puestos();

/*
* puesto que se muestre tenga la carasterica que se busca List<Caracteristicas> -> List<Puestos>puestos que las tengan
*
* */

}
