package BolsaEmpleo.logic;

import BolsaEmpleo.logic.Base.Empresa;
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
public class Puesto {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    private String descripcion;
    private Integer salario;
    private String tipo;
    private String estado;
    private String fecha;

}
