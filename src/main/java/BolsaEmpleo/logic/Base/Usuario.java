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
    
    private String username;
    private String clave;
    private String tipo;
}


