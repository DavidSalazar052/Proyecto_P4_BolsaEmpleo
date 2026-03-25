package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Empresa;
import BolsaEmpleo.logic.Base.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OferentesRepository extends JpaRepository<Oferente, Integer> {
    @Query("select e from Oferente e where e.aprobado = true")
    public List<Oferente> findAllByAprobadaOferente();

    @Query("select e from Empresa e where e.aprobada = false")
    public List<Oferente> findAllByNoAprobada();

    @Query("select e from Empresa e where e.nombre like %?1%")
    public List<Oferente> findByNombre(String nombre);

}
