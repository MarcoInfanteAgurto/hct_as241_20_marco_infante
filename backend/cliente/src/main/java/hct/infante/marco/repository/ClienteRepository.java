package hct.infante.marco.repository;

import hct.infante.marco.model.Cliente;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String> {
}
