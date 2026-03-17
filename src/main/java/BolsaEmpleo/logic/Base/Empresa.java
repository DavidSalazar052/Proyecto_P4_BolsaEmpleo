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

public class Empresa extends Usuario {
    @Id
    private String id;

    private String nombre;
    private String localizacion;
    private String correo;
    private String telefono;
    private String descripcion;

    //Para que el Administrador la apruebe
    private boolean aprobada = false;
}

/*
* ID(Pk)- Nombre -localización-correo-telefono-descripción-aprobada
*
* */