package com.tedblob.reactive.dao;


import com.tedblob.reactive.model.Customer;
import com.tedblob.reactive.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerDAO {


    @Autowired
    CustomerRepository customerRepository;

    public Flux<Customer> getCustomers() {

        Flux<Customer> customers = customerRepository.findAll();


        return customers;

    }

    public Mono<Customer> getCustomerById(String id) {

        Mono<Customer> customerMono = customerRepository.findById(id);

        return customerMono;

    }


}
