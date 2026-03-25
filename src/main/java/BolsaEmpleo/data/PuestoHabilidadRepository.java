package BolsaEmpleo.data;
import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Puesto;
import BolsaEmpleo.logic.PuestoHabilidades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PuestoHabilidadRepository extends JpaRepository<PuestoHabilidades, Integer> {
}


