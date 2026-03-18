package BolsaEmpleo.logic.Base;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Administrador {
    @Id
    private String id;//PK FK

    @OneToOne
    @JoinColumn
    private Usuario usuario;
    private String identificacion; // cedula
    private String nombre;
    private String correo;


}
