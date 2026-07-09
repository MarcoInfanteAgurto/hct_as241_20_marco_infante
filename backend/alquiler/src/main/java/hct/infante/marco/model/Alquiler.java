package hct.infante.marco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alquiler")
public class Alquiler {

    @Id
    private String id; // automático

    private String clienteId;
    private String vehiculoId;
    private int dias;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double total;

    private String estado = "ACTIVO"; // por defecto
}
