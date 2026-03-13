package BolsaEmpleo.data;

import BolsaEmpleo.data.entity.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OferentesRepository extends JpaRepository<Oferente, String> {
}
