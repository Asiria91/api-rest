package com.api.rest.pruebatecnica.repository;

import com.api.rest.pruebatecnica.documents.TipoCita;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TipoCitaRepository extends ReactiveMongoRepository<TipoCita, String> {
    Mono<TipoCita> findFirstByName(String name);

}
