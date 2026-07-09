package hct.infante.marco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cliente")
public class Cliente {

    @Id
    private String id; // MongoDB lo genera solo

    private String dni;
    private String nombres;
    private String apellidos;
    private String celular;
    private String correo;
    private String licencia;

    private String estado = "ACTIVO"; // valor por defecto
}
