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


public abstract class Usuario {
    @Id
    private String id;

    private String username;
    private String clave;
    private String tipo;
}
