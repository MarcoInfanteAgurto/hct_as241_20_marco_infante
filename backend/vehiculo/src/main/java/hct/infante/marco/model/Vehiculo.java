package hct.infante.marco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehiculo")
public class Vehiculo {

    @Id
    private String id; // MongoDB lo genera solo (ObjectId)

    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private double precioPoDia;

    private String estado = "DISPONIBLE"; // valor por defecto al crear el objeto
}
