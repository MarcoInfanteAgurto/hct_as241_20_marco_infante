package hct.infante.marco.service.Impl;

import hct.infante.marco.model.Vehiculo;
import hct.infante.marco.repository.VehiculoRepository;
import hct.infante.marco.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VehiculoServiceImpl implements VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public Flux<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    @Override
    public Mono<Vehiculo> buscarPorId(String id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public Mono<Vehiculo> crear(Vehiculo vehiculo) {
        vehiculo.setId(null);          // Mongo genera uno nuevo
        vehiculo.setEstado("DISPONIBLE");  // estado inicial forzado
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Mono<Vehiculo> actualizar(String id, Vehiculo vehiculo) {
        return vehiculoRepository.findById(id)
                .flatMap(existente -> {
                    existente.setPlaca(vehiculo.getPlaca());
                    existente.setMarca(vehiculo.getMarca());
                    existente.setModelo(vehiculo.getModelo());
                    existente.setAnio(vehiculo.getAnio());
                    existente.setColor(vehiculo.getColor());
                    existente.setPrecioPoDia(vehiculo.getPrecioPoDia());
                    return vehiculoRepository.save(existente);
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return vehiculoRepository.deleteById(id);
    }

    @Override
    public Mono<Vehiculo> activar(String id) {
        return vehiculoRepository.findById(id)
                .flatMap(v -> {
                    v.setEstado("DISPONIBLE");
                    return vehiculoRepository.save(v);
                });
    }

    @Override
    public Mono<Vehiculo> desactivar(String id) {
        return vehiculoRepository.findById(id)
                .flatMap(v -> {
                    v.setEstado("NO_DISPONIBLE");
                    return vehiculoRepository.save(v);
                });
    }
}
