package hct.infante.marco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    private String id;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private double precioPoDia;
    private String estado;
}
