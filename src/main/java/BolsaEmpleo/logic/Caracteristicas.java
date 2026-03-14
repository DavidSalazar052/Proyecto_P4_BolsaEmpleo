package BolsaEmpleo.logic;


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
@NoArgsConstructor
@AllArgsConstructor
public class Caracteristicas {
    @Id
    private String id;

    private String nombre;
    // esto me lo tiro el Intellij para el tema de la relación ciclica (josue)
    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Caracteristicas Padre;



}
