package hct.infante.marco.service;

import hct.infante.marco.model.Alquiler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlquilerService {

    Flux<Alquiler> listar();

    Mono<Alquiler> buscarPorId(String id);

    Mono<Alquiler> crear(Alquiler alquiler);

    Mono<Alquiler> actualizar(String id, Alquiler alquiler);

    Mono<Void> eliminar(String id);

    Mono<Alquiler> activar(String id);

    Mono<Alquiler> desactivar(String id);
}
