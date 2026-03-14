package BolsaEmpleo.data;

import BolsaEmpleo.logic.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OferentesRepository extends JpaRepository<Oferente, String> {
}
