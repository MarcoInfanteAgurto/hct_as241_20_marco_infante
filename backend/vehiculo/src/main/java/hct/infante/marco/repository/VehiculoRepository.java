package hct.infante.marco.repository;

import hct.infante.marco.model.Vehiculo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VehiculoRepository extends ReactiveMongoRepository<Vehiculo, String> {
}
