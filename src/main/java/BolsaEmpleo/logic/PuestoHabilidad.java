package BolsaEmpleo.logic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuestoHabilidad {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "puesto_id")
    private  PuestoEmpresa puesto;
    private Caracteristicas habilidad;
    private Integer nivel;



}
