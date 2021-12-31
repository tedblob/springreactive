package com.tedblob.reactive.controllers;


import com.tedblob.reactive.model.Customer;
import com.tedblob.reactive.model.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Repeat;

@RestController
@Slf4j
public class CustomerRestController {


    @Autowired
    CustomerRepository customerRepository;


    @GetMapping(path = "/customers")
    public Flux<Customer> handleGetCustomers() {

        return customerRepository.findAll();

    }

    @GetMapping(path = "/customers/{customerId}")
    public Mono<ResponseEntity<Customer>> handleGetCustomer(@PathVariable String customerId) {

//        Mono<ResponseEntity<Customer>> responseEntityMono = Mono
//                .just(new ResponseEntity<Customer>(
//                        new Customer(), HttpStatus.NOT_FOUND));
        Mono<ResponseEntity<Customer>> responseEntityMono;
        responseEntityMono = customerRepository.findById(customerId)
                .map(x -> new ResponseEntity<Customer>(x, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return responseEntityMono;

    }

    private boolean validateResult(Customer x) {
        return x.getName() != null;
    }

    @PostMapping(path = "/customers")
    public Mono<Customer> handlePostCustomer(@RequestBody Customer customer) {

        return customerRepository.save(customer);

    }


    @DeleteMapping(path = "/customers/{customerId}")
    public Mono<Void> handleDeleteCustomer(@PathVariable String customerId) {

        return customerRepository.deleteById(customerId);
    }


    @PutMapping(path = "/customers/{customerId}")
    public Mono<ResponseEntity<Customer>> handlePutCustomer(@PathVariable String customerId,
                                                            @RequestBody Customer customer) {

        return
                customerRepository.findById(customerId)
                        .flatMap(x -> {

                            x.setName(customer.getName());
                            Mono<Customer> updatedCustomer = customerRepository.save(x);
                            return updatedCustomer;

                        })
                        .repeatWhenEmpty(Repeat.times(5))
                        .map(x -> new ResponseEntity<>(x, HttpStatus.OK))
                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


}
