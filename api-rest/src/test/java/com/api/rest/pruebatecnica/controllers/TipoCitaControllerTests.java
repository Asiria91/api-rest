package com.api.rest.pruebatecnica.controllers;

import com.api.rest.pruebatecnica.documents.TipoCita;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoCitaControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private TipoCita tipocitaGuardado;

    @Test
    @Order(0)
    public void testGuardarTipoCita(){
        Flux<TipoCita> tipoCitaFlux = webTestClient.post()
                .uri("/api/v1/tipocitas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new TipoCita("prueba","prueba","992922","#f0001")))
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(TipoCita.class).getResponseBody()
                .log();

        tipoCitaFlux.next().subscribe(tipoCita -> {
            this.tipocitaGuardado = tipoCita;
        });

        Assertions.assertNotNull(tipocitaGuardado);
    }


    @Test
    @Order(1)
    public void testActualizarTipoCita(){
        Flux<TipoCita> tipocitaFlux = webTestClient.put()
                .uri("/api/v1/tipocitas/{id}",tipocitaGuardado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new TipoCita(tipocitaGuardado.getId(),"prueba","23232","#f0001")))
                .exchange()
                .returnResult(TipoCita.class).getResponseBody()
                .log();

        StepVerifier.create(tipocitaFlux)
                .expectSubscription()
                .expectNextMatches(tipoCita -> tipoCita.getDescription().equals("prueba"))
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void testListarTipoCita(){
        Flux<TipoCita> tipoCitaFlux = webTestClient.get()
                .uri("/api/v1/tipocitas")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(TipoCita.class).getResponseBody()
                .log();

        StepVerifier.create(tipoCitaFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testEliminarTipoCita(){
        Flux<Void> flux = webTestClient.delete()
                .uri("/api/v1/tipocitas/{id}",tipocitaGuardado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Void.class).getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .verifyComplete();
    }
}
