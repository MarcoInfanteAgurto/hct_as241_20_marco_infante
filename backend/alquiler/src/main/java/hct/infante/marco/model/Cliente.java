package hct.infante.marco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String celular;
    private String correo;
    private String licencia;
    private String estado;
}
