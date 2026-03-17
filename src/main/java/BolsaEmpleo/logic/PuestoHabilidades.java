package BolsaEmpleo.logic;

import BolsaEmpleo.logic.Base.Caracteristicas;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuestoHabilidades {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "puesto_id")
    private Puesto puesto;

    @OneToOne
    @JoinColumn(name = "caracteristica_id")
    private Caracteristicas habilidad;
    private Integer nivel;



}
