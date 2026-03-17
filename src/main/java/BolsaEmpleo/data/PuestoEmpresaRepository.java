package BolsaEmpleo.data;

import BolsaEmpleo.logic.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuestoEmpresaRepository extends JpaRepository<Puesto,String>  {

}
