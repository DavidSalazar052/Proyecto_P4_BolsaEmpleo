package BolsaEmpleo.logic;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
public class PuestoEmpresa {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToMany
    @JoinColumn(name = "habilidad_id")
    List<HabilidadEmpresa> habilidades;

    private String descripcion;
    private Integer salario;
    private String tipo;
    private String estado;
    private String fecha;

}
