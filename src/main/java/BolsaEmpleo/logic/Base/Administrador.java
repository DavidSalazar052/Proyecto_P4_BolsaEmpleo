package BolsaEmpleo.logic.Base;

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

public class Administrador extends Usuario {
    @Id
    private String id;//PK FK

    private String identificacion; // cedula
    private String nombre;
    private String correo;


}
