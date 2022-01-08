package com.tedblob.reactive.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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

    @GetMapping(path = "/monoJust", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Integer> getMonoJust() throws InterruptedException {
        System.out.println("Thread : " + Thread.currentThread().getName());

        Mono<Integer> optionalMono = Mono.just(asyncMethod())
                        .doOnNext(System.out::println)
                        .log();

        System.out.println("Thread : " + Thread.currentThread().getName() + " is leaving");
        return optionalMono;
    }

    @GetMapping(path = "/monoJustOrEmpty", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Integer> getMonoJustOrEmptyOpt() throws InterruptedException {
        System.out.println("Thread : " + Thread.currentThread().getName());

        Mono<Integer> optionalMono = Mono.justOrEmpty(asyncOptionalMethod())
                .doOnNext(System.out::println)
                .defaultIfEmpty(1000)
                .log();

        System.out.println("Thread : " + Thread.currentThread().getName() + " is leaving");
        return optionalMono;
    }
    @GetMapping(path = "/monoJustOrEmptyT", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Integer> getMonoJustOrEmptyT() throws InterruptedException {
        System.out.println("Thread : " + Thread.currentThread().getName());

        Mono<Integer> optionalMono = Mono.justOrEmpty(asyncMethod())
                .defaultIfEmpty(1000).log();

        System.out.println("Thread : " + Thread.currentThread().getName() + " is leaving");
        return optionalMono;
    }

    private Integer asyncMethod() {
        // some calculation
        return null;
    }
    private Optional<Integer> asyncOptionalMethod() {
        // some calculation
        return Optional.of(8978);
    }

    @GetMapping(path = "/int-mono")
    public Mono<Integer> handleGetMono() {

        return Mono.just(5);
    }


}
