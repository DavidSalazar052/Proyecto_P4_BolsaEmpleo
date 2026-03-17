package BolsaEmpleo.logic;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Oferente;
import jakarta.persistence.Id;

import java.util.List;

public class OferenteHabilidades {

    @Id
    private String id;

    private Oferente oferente;
    private Caracteristicas caracteristicas;
}
