package hct.infante.marco.rest;

import hct.infante.marco.model.Alquiler;
import hct.infante.marco.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerRest {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public Flux<Alquiler> listar() {
        return alquilerService.listar();
    }

    @GetMapping("/{id}")
    public Mono<Alquiler> buscarPorId(@PathVariable String id) {
        return alquilerService.buscarPorId(id);
    }

    @PostMapping
    public Mono<Alquiler> crear(@RequestBody Alquiler alquiler) {
        return alquilerService.crear(alquiler);
    }

    @PutMapping("/{id}")
    public Mono<Alquiler> actualizar(@PathVariable String id, @RequestBody Alquiler alquiler) {
        return alquilerService.actualizar(id, alquiler);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return alquilerService.eliminar(id);
    }

    @PutMapping("/{id}/desactivar")
    public Mono<Alquiler> desactivar(@PathVariable String id) {
        return alquilerService.desactivar(id);
    }

    @PutMapping("/{id}/activar")
    public Mono<Alquiler> activar(@PathVariable String id) {
        return alquilerService.activar(id);
    }
}
