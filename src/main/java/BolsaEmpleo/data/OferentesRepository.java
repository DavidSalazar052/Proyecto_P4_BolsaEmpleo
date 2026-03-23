package BolsaEmpleo.data;


import BolsaEmpleo.logic.Base.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OferentesRepository extends JpaRepository<Oferente, String> {
    @Query("select e from Oferente e where e.aprobado = true")
    public List<Oferente> findAllByAprobadaOferente();

    @Query("select e from Oferente e where e.aprobado = false")
    public List<Oferente> findAllByNoAprobadaOferente();
}
