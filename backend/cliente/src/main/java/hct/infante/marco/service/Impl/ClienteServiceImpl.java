package hct.infante.marco.service.Impl;

import hct.infante.marco.model.Cliente;
import hct.infante.marco.repository.ClienteRepository;
import hct.infante.marco.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Flux<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public Mono<Cliente> buscarPorId(String id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Mono<Cliente> crear(Cliente cliente) {
        cliente.setId(null);          // Mongo genera uno nuevo
        cliente.setEstado("ACTIVO");  // estado inicial forzado
        return clienteRepository.save(cliente);
    }

    @Override
    public Mono<Cliente> actualizar(String id, Cliente cliente) {
        return clienteRepository.findById(id)
                .flatMap(existente -> {
                    existente.setDni(cliente.getDni());
                    existente.setNombres(cliente.getNombres());
                    existente.setApellidos(cliente.getApellidos());
                    existente.setCelular(cliente.getCelular());
                    existente.setCorreo(cliente.getCorreo());
                    existente.setLicencia(cliente.getLicencia());
                    return clienteRepository.save(existente);
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return clienteRepository.deleteById(id);
    }

    @Override
    public Mono<Cliente> activar(String id) {
        return clienteRepository.findById(id)
                .flatMap(c -> {
                    c.setEstado("ACTIVO");
                    return clienteRepository.save(c);
                });
    }

    @Override
    public Mono<Cliente> desactivar(String id) {
        return clienteRepository.findById(id)
                .flatMap(c -> {
                    c.setEstado("INACTIVO");
                    return clienteRepository.save(c);
                });
    }
}
