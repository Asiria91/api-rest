package com.api.rest.pruebatecnica.controllers;

import com.api.rest.pruebatecnica.documents.TipoCita;
import com.api.rest.pruebatecnica.repository.TipoCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1")
public class TipoCitaController {

    @Autowired
    private TipoCitaRepository tipoCitaRepository;

    @GetMapping("/tipocitas")
    public Flux<TipoCita> listarTipoCitas(){
        return tipoCitaRepository.findAll();
    }

    @GetMapping("/tipocitas/{id}")
    public Mono<ResponseEntity<TipoCita>> obtenerTipoCita(@PathVariable String id){
        return tipoCitaRepository.findById(id)
                .map(tipoCita -> new ResponseEntity<>(tipoCita, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/tipocitas")
    public Mono<ResponseEntity<TipoCita>> guardarContacto(@RequestBody TipoCita tipocita){
        return tipoCitaRepository.insert(tipocita)
                .map(contactoGuardado -> new ResponseEntity<>(contactoGuardado,HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(tipocita,HttpStatus.NOT_ACCEPTABLE));
    }

    @PutMapping("/tipocitas/{id}")
    public Mono<ResponseEntity<TipoCita>> actualizarContacto(@RequestBody TipoCita contacto,@PathVariable String id){
        return tipoCitaRepository.findById(id)
                .flatMap(contactoActualizado -> {
                    contacto.setId(id);
                    return tipoCitaRepository.save(contacto)
                            .map(contacto1 -> new ResponseEntity<>(contacto1,HttpStatus.ACCEPTED));
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/tipocitas/{id}")
    public Mono<Void> eliminarContacto(@PathVariable String id){
        return tipoCitaRepository.deleteById(id);
    }
}

