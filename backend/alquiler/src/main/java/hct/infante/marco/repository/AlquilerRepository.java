package hct.infante.marco.repository;

import hct.infante.marco.model.Alquiler;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AlquilerRepository extends ReactiveMongoRepository<Alquiler, String> {
}
