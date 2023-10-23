package com.api.rest.pruebatecnica.repository;

import com.api.rest.pruebatecnica.documents.TipoCita;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoCitaRepositoryTests {

    @Autowired
    private TipoCitaRepository tipoCitaRepository;

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    @BeforeAll
    public void insertarDatos(){
        TipoCita tipoCita = new TipoCita();
        tipoCita.setName("prueba");
        tipoCita.setDescription("test tipo cita");
        tipoCita.setDurationMinutes("23232");
        tipoCita.setColorHexDecimal("#f001");

        TipoCita tipoCita2 = new TipoCita();
        tipoCita2.setName("prueba2");
        tipoCita2.setDescription("test tipo cita");
        tipoCita2.setDurationMinutes("2323245");
        tipoCita2.setColorHexDecimal("#f002");

        TipoCita tipoCita3 = new TipoCita();
        tipoCita3.setName("prueba3");
        tipoCita3.setDescription("test tipo cita");
        tipoCita3.setDurationMinutes("53534");
        tipoCita3.setColorHexDecimal("#f003");

        //Guardamos las tipo de citas
        StepVerifier.create(tipoCitaRepository.insert(tipoCita).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(tipoCitaRepository.save(tipoCita2).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(tipoCitaRepository.save(tipoCita3).log())
                .expectSubscription()
                .expectNextMatches(tipocita -> (tipocita.getId() != null))
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void testListarTipoCita(){
        StepVerifier.create(tipoCitaRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }


    @Test
    @Order(2)
    public void testActualizarTipoCita(){
        Mono<TipoCita> tipocitaActualizado = tipoCitaRepository.findFirstByName("prueba")
                .map(tipoCita -> {
                    tipoCita.setDescription("cambio de prueba");
                    return tipoCita;
                }).flatMap(tipoCita -> {
                    return tipoCitaRepository.save(tipoCita);
                });

        StepVerifier.create(tipocitaActualizado.log())
                .expectSubscription()
                .expectNextMatches(tipocita -> (tipocita.getDescription().equals("cambio de prueba")))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testEliminarTipoCitaPorId(){
        Mono<Void> tipocitaEliminado = tipoCitaRepository.findFirstByName("prueba")
                .flatMap(tipoCita -> {
                    return tipoCitaRepository.deleteById(tipoCita.getId());
                }).log();

        StepVerifier.create(tipocitaEliminado)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void testEliminarTipoCita(){
        Mono<Void> tipocitaEliminado = tipoCitaRepository.findFirstByName("prueba")
                .flatMap(tipoCita -> tipoCitaRepository.delete(tipoCita));

        StepVerifier.create(tipocitaEliminado)
                .expectSubscription()
                .verifyComplete();
    }

    @AfterAll
    public void limpiarDatos(){
        Mono<Void> elementosEliminados = tipoCitaRepository.deleteAll();
        StepVerifier.create(elementosEliminados.log())
                .expectSubscription()
                .verifyComplete();
    }
}
