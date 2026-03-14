package BolsaEmpleo.data;

import BolsaEmpleo.logic.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
