package hct.infante.marco.rest;

import hct.infante.marco.model.Vehiculo;
import hct.infante.marco.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoRest {

    @Autowired
    private VehiculoService vehiculoService; // interfaz, no la Impl

    @GetMapping
    public Flux<Vehiculo> listar() {
        return vehiculoService.listar();
    }

    @GetMapping("/{id}")
    public Mono<Vehiculo> buscarPorId(@PathVariable String id) {
        return vehiculoService.buscarPorId(id);
    }

    @PostMapping
    public Mono<Vehiculo> crear(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.crear(vehiculo);
    }

    @PutMapping("/{id}")
    public Mono<Vehiculo> actualizar(@PathVariable String id, @RequestBody Vehiculo vehiculo) {
        return vehiculoService.actualizar(id, vehiculo);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return vehiculoService.eliminar(id);
    }

    @PutMapping("/{id}/desactivar")
    public Mono<Vehiculo> desactivar(@PathVariable String id) {
        return vehiculoService.desactivar(id);
    }

    @PutMapping("/{id}/activar")
    public Mono<Vehiculo> activar(@PathVariable String id) {
        return vehiculoService.activar(id);
    }
}
