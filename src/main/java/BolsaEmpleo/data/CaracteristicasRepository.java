package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Caracteristicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaracteristicasRepository  extends JpaRepository<Caracteristicas,String> {
    // Solo las raíces — las que no tienen padre
    @Query("select c from Caracteristicas c where c.Padre is null")
    List<Caracteristicas> findRaices();

    // Hijos directos de una categoría
    @Query("select c from Caracteristicas c where c.Padre.id = ?1")
    List<Caracteristicas> findHijos(String padreId);
}
