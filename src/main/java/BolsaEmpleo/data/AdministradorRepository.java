package BolsaEmpleo.data;

import BolsaEmpleo.data.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador,String> { }
