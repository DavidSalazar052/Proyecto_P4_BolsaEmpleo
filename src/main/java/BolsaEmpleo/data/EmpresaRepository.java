package BolsaEmpleo.data;

import BolsaEmpleo.logic.Base.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
