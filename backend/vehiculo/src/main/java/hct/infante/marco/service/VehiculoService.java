package hct.infante.marco.service;

import hct.infante.marco.model.Vehiculo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehiculoService {

    Flux<Vehiculo> listar();

    Mono<Vehiculo> buscarPorId(String id);

    Mono<Vehiculo> crear(Vehiculo vehiculo);

    Mono<Vehiculo> actualizar(String id, Vehiculo vehiculo);

    Mono<Void> eliminar(String id);

    Mono<Vehiculo> activar(String id);

    Mono<Vehiculo> desactivar(String id);
}
