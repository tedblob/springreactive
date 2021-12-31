package com.tedblob.reactive.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FluxAndMonoController {


    @GetMapping(path = "/int-flux", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Integer> handleGetIntFlux() throws InterruptedException {
        System.out.println("Thread : " + Thread.currentThread().getName());
        Flux<Integer> integerFlux =
                Flux
                        .just(1, 2, 3, 4, 5, 6)
                        .map(x -> {
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
////                            System.out.println("The publisher is publishing the values in : " + Thread.currentThread().getName());
                            return x + 1;
                        })
                        .log();
//        Mono<List<Integer>> listMono = integerFlux.collectList();
//        List<Integer> integerList = listMono.block();

        System.out.println("Thread : " + Thread.currentThread().getName() + " is leaving");
        return integerFlux;
    }


    @GetMapping(path = "/int-mono")
    public Mono<Integer> handleGetMono() {

        return Mono.just(5);
    }


}
