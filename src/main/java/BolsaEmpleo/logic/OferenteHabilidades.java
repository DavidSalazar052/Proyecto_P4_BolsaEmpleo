package BolsaEmpleo.logic;

import BolsaEmpleo.logic.Base.Caracteristicas;
import BolsaEmpleo.logic.Base.Oferente;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class OferenteHabilidades {

    @Id
    private String id;
    @OneToOne
    private Oferente oferente;
    @OneToOne
    private Caracteristicas caracteristicas;
}
