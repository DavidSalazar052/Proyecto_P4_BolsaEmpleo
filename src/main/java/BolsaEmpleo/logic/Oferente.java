package BolsaEmpleo.logic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Oferente {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String telefono;
    private String correo;

    //Para que el Administrador la apruebe
    private boolean aprobado = false;
}
