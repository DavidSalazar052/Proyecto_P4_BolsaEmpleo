package BolsaEmpleo.data;

import BolsaEmpleo.logic.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuestoRepository extends JpaRepository<Puesto,String>  {
}
