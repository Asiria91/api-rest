package com.api.rest.pruebatecnica.functional;

import com.api.rest.pruebatecnica.documents.TipoCita;
import com.api.rest.pruebatecnica.repository.TipoCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class TipoCitaHandler {

    @Autowired
    private TipoCitaRepository tipoCitaRepository;

    private Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    public Mono<ServerResponse> listarTipoCita(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tipoCitaRepository.findAll(), TipoCita.class);
    }
    public Mono<ServerResponse> obtenerTipoCitaPorId(ServerRequest request){
        String id = request.pathVariable("id");

        return tipoCitaRepository.findById(id)
                .flatMap(contacto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(contacto)))
                .switchIfEmpty(response404);
    }
    public Mono<ServerResponse> insertarTipoCita(ServerRequest request){
        Mono<TipoCita> contactoMono = request.bodyToMono(TipoCita.class);

        return contactoMono
                .flatMap(contacto -> tipoCitaRepository.save(contacto)
                        .flatMap(contactoGuardado -> ServerResponse.accepted()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(contactoGuardado))))
                .switchIfEmpty(response406);
    }
    public Mono<ServerResponse> actualizarTipoCita(ServerRequest request){
        Mono<TipoCita> contactoMono = request.bodyToMono(TipoCita.class);
        String id = request.pathVariable("id");

        Mono<TipoCita> contactoActualizado = contactoMono.flatMap(tipocita ->
                tipoCitaRepository.findById(id)
                        .flatMap(oldTipocita -> {
                            oldTipocita.setDescription(tipocita.getDescription());
                            oldTipocita.setName(tipocita.getName());
                            oldTipocita.setColorHexDecimal(tipocita.getColorHexDecimal());
                            oldTipocita.setDurationMinutes(tipocita.getDurationMinutes());
                            return tipoCitaRepository.save(oldTipocita);
                        }));

        return contactoActualizado.flatMap(tipoCita ->
                ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(tipoCita)))
                .switchIfEmpty(response404);
    }
    public Mono<ServerResponse> eliminarTipoCita(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Void> tipoCitaEliminado = tipoCitaRepository.deleteById(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tipoCitaEliminado,Void.class);
    }
}
