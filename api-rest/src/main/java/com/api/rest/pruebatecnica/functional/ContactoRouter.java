package com.api.rest.pruebatecnica.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ContactoRouter {

    @Bean
    public RouterFunction<ServerResponse> routeContacto(TipoCitaHandler tipoCitaHandler){
        return RouterFunctions
                .route(GET("/functional/tipoCita/"), tipoCitaHandler::listarTipoCita)
                .andRoute(GET("/functional/tipoCita/{id}"), tipoCitaHandler::obtenerTipoCitaPorId)
                .andRoute(POST("/functional/tipoCita/"), tipoCitaHandler::insertarTipoCita)
                .andRoute(PUT("/functional/tipoCita/{id}"), tipoCitaHandler::actualizarTipoCita)
                .andRoute(DELETE("/functional/tipoCita/{id}"), tipoCitaHandler::eliminarTipoCita);
    }

}
