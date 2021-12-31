package com.tedblob.reactive.functionalweb;


import com.tedblob.reactive.model.Customer;
import com.tedblob.reactive.model.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerAPIHandler {


    @Autowired
    CustomerRepository customerRepository;

    public Mono<ServerResponse> handleGetAllCustomers(ServerRequest serverRequest) {

        log.info("in handleGetAllCustomers .... handler....");

        return
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerRepository.findAll(), Customer.class);

    }

    public Mono<ServerResponse> handleGetCustomerById(ServerRequest serverRequest) {

        String customerId = serverRequest.pathVariable("customerId");

        Mono<Customer> customerMono = customerRepository.findById(customerId);

        Mono<ServerResponse> responseMono = customerMono
                .flatMap(x -> {

                    Mono<ServerResponse> serverResponse = ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    BodyInserters.fromPublisher(Mono.just(x), Customer.class)
                            );

                    return serverResponse;
                })
                .switchIfEmpty(ServerResponse.notFound().build());

        return responseMono;
    }

    public Mono<ServerResponse> handleCreateCustomer(ServerRequest serverRequest) {

        Mono<Customer> customer = serverRequest.bodyToMono(Customer.class);

//        Mono<Customer> newCustomer =
//                customer
//                        .flatMap(x -> {
//
//                            Mono<Customer> newlyAddedCustomer = customerRepository.save(x);
//
//                            return newlyAddedCustomer;
//                        });
        Mono<Customer> newCustomer =
                customer
                        .flatMap(x -> customerRepository.save(x));


        return
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(newCustomer, Customer.class);


    }


}
