package hct.infante.marco.service;

import hct.infante.marco.model.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Flux<Cliente> listar();

    Mono<Cliente> buscarPorId(String id);

    Mono<Cliente> crear(Cliente cliente);

    Mono<Cliente> actualizar(String id, Cliente cliente);

    Mono<Void> eliminar(String id);

    Mono<Cliente> activar(String id);

    Mono<Cliente> desactivar(String id);
}
