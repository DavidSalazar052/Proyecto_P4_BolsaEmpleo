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

public class Oferente extends Usuario {
    @Id
    private String id;

    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String telefono;
    private String correo;
    private String residencia;

    //Para que el Administrador la apruebe
    private boolean aprobado = false;
}
/*
* ID(pk) - Nombre - Apellido - Pais - Telefono -correo -residencia -aprobada
* */