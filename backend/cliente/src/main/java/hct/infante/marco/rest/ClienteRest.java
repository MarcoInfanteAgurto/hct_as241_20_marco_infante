package hct.infante.marco.rest;

import hct.infante.marco.model.Cliente;
import hct.infante.marco.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRest {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Flux<Cliente> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public Mono<Cliente> buscarPorId(@PathVariable String id) {
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public Mono<Cliente> crear(@RequestBody Cliente cliente) {
        return clienteService.crear(cliente);
    }

    @PutMapping("/{id}")
    public Mono<Cliente> actualizar(@PathVariable String id, @RequestBody Cliente cliente) {
        return clienteService.actualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return clienteService.eliminar(id);
    }

    @PutMapping("/{id}/desactivar")
    public Mono<Cliente> desactivar(@PathVariable String id) {
        return clienteService.desactivar(id);
    }

    @PutMapping("/{id}/activar")
    public Mono<Cliente> activar(@PathVariable String id) {
        return clienteService.activar(id);
    }
}
