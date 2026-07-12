package hct.infante.marco.service.Impl;

import hct.infante.marco.model.Alquiler;
import hct.infante.marco.model.Cliente;
import hct.infante.marco.model.Vehiculo;
import hct.infante.marco.repository.AlquilerRepository;
import hct.infante.marco.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    @Qualifier("vehiculoWebClient")
    private WebClient vehiculoWebClient;

    @Autowired
    @Qualifier("clienteWebClient")
    private WebClient clienteWebClient;

    @Override
    public Flux<Alquiler> listar() {
        return alquilerRepository.findAll();
    }

    @Override
    public Mono<Alquiler> buscarPorId(String id) {
        return alquilerRepository.findById(id);
    }

    @Override
    public Mono<Alquiler> crear(Alquiler alquiler) {
        // 1. Validar que el cliente exista
        Mono<Cliente> clienteMono = clienteWebClient.get()
                .uri("/api/clientes/{id}", alquiler.getClienteId())
                .retrieve()
                .bodyToMono(Cliente.class);

        // 2. Validar que el vehiculo exista
        Mono<Vehiculo> vehiculoMono = vehiculoWebClient.get()
                .uri("/api/vehiculos/{id}", alquiler.getVehiculoId())
                .retrieve()
                .bodyToMono(Vehiculo.class);

        // 3. Combinar ambas llamadas y armar el alquiler
        return Mono.zip(clienteMono, vehiculoMono)
                .flatMap(tupla -> {
                    Cliente cliente = tupla.getT1();
                    Vehiculo vehiculo = tupla.getT2();

                    if (!"ACTIVO".equals(cliente.getEstado())) {
                        return Mono.error(new RuntimeException("El cliente no está activo"));
                    }
                    if (!"DISPONIBLE".equals(vehiculo.getEstado())) {
                        return Mono.error(new RuntimeException("El vehiculo no está disponible"));
                    }

                    // calcular total
                    double total = vehiculo.getPrecioPoDia() * alquiler.getDias();
                    alquiler.setId(null);
                    alquiler.setTotal(total);
                    alquiler.setEstado("ACTIVO");

                    // 4. Cambiar el estado del vehiculo a NO_DISPONIBLE tras alquilarlo
                    Mono<Vehiculo> desactivarVehiculoMono = vehiculoWebClient.put()
                            .uri("/api/vehiculos/{id}/desactivar", vehiculo.getId())
                            .retrieve()
                            .bodyToMono(Vehiculo.class);

                    return desactivarVehiculoMono.then(alquilerRepository.save(alquiler));
                });
    }

    @Override
    public Mono<Alquiler> actualizar(String id, Alquiler alquiler) {
        return alquilerRepository.findById(id)
                .flatMap(existente -> {
                    String vehiculoAnteriorId = existente.getVehiculoId();
                    String vehiculoNuevoId = alquiler.getVehiculoId();

                    Mono<Cliente> clienteMono = clienteWebClient.get()
                            .uri("/api/clientes/{id}", alquiler.getClienteId())
                            .retrieve()
                            .bodyToMono(Cliente.class);

                    Mono<Vehiculo> vehiculoMono = vehiculoWebClient.get()
                            .uri("/api/vehiculos/{id}", vehiculoNuevoId)
                            .retrieve()
                            .bodyToMono(Vehiculo.class);

                    return Mono.zip(clienteMono, vehiculoMono)
                            .flatMap(tupla -> {
                                Cliente cliente = tupla.getT1();
                                Vehiculo vehiculo = tupla.getT2();

                                double total = vehiculo.getPrecioPoDia() * alquiler.getDias();

                                existente.setClienteId(alquiler.getClienteId());
                                existente.setVehiculoId(vehiculoNuevoId);
                                existente.setDias(alquiler.getDias());
                                existente.setFechaInicio(alquiler.getFechaInicio());
                                existente.setFechaFin(alquiler.getFechaFin());
                                existente.setTotal(total);

                                if (!vehiculoAnteriorId.equals(vehiculoNuevoId)) {
                                    Mono<Vehiculo> desactivarNuevoVehiculoMono = vehiculoWebClient.put()
                                            .uri("/api/vehiculos/{id}/desactivar", vehiculoNuevoId)
                                            .retrieve()
                                            .bodyToMono(Vehiculo.class);

                                    Mono<Vehiculo> activarAnteriorVehiculoMono = vehiculoWebClient.put()
                                            .uri("/api/vehiculos/{id}/activar", vehiculoAnteriorId)
                                            .retrieve()
                                            .bodyToMono(Vehiculo.class);

                                    return desactivarNuevoVehiculoMono
                                            .then(activarAnteriorVehiculoMono)
                                            .then(alquilerRepository.save(existente));
                                }

                                return alquilerRepository.save(existente);
                            });
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return alquilerRepository.deleteById(id);
    }

    @Override
    public Mono<Alquiler> activar(String id) {
        return alquilerRepository.findById(id)
                .flatMap(a -> {
                    a.setEstado("ACTIVO");
                    return alquilerRepository.save(a);
                });
    }

    @Override
    public Mono<Alquiler> desactivar(String id) {
        return alquilerRepository.findById(id)
                .flatMap(a -> {
                    a.setEstado("INACTIVO");
                    return alquilerRepository.save(a);
                });
    }
}
