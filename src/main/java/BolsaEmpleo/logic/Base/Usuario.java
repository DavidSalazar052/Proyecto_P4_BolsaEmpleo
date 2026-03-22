package BolsaEmpleo.logic.Base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Usuario {
    @Id
    private String id;
    /*
    * nota (josue):
    * Si este username se le deja sin @Unique dentremos varios usuarios con el mismo nombre pero diferente Contraseña
    * O le ponemos que sea @Unique en el UsuarioRepository quitamos List<Usuario>
    * */
    @Column(unique = true)
    private String username; // oferante - admin - empresa

    private String clave; //*****
    private String tipo; //ADM - OFE- EMP
}


