package com.tedblob.reactive;

import com.tedblob.reactive.model.Customer;
import com.tedblob.reactive.model.CustomerAddress;
import com.tedblob.reactive.model.CustomerAddressRepository;
import com.tedblob.reactive.model.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressRepository addressRepo;


    public List<Customer> customerData() {
        // @formatter:off

        return Arrays.asList(new Customer("101", "Verizon"),
                new Customer("102", "IBM"),
                new Customer("103", "Wipro"),
                new Customer("104", "JP Morgan"),
                new Customer("105", "Morgan Stanley"));

        // @formatter:on

    }

    public List<CustomerAddress> addressData() {

        // @formatter:off


        return Arrays.asList(new CustomerAddress("9", "101", "101 line1", "101 line2", "101 city", "P"),
                new CustomerAddress("1", "101", "101 line1-a1", "101 line2-a1", "101 city-a1", "P"),
                new CustomerAddress("2", "105", "105 line1-a1", "105 line2", "105 city-a2", "D"),
                new CustomerAddress("3", "102", "102 line1", "102 line2", "102 city", "P"),
                new CustomerAddress("4", "103", "103 line1-1", "103 line2-1", "103 city-1", "P"),
                new CustomerAddress("5", "103", "103 line1-2", "103 line2-2", "103 city-2", "D"),
                new CustomerAddress("6", "103", "103 line1-3", "103 line2-3", "103 city-3", "P"),
                new CustomerAddress("7", "104", "104 line1", "104 line2", "104 city", "D"));

    }


    @Override
    public void run(String... args) throws Exception {
        log.info("The initializer is executed...");
        initialize();
    }

    private void initialize() {

        /***
         * 1) delete all records
         * 2) insert : create customer records from the arrayList (customerData)
         * 3) findAll and retrieve all records and print them ...
         *    just to ensure that the objects were added
         */

        Mono<Void> deletedObjects = customerRepository.deleteAll();
//
//        for (int i = 0; i < customerData().size(); i++) {
//
//            Customer c = customerData().get(i);
//            Mono<Customer> newlyAddedCustomer = customerRepository.save(c);
//
//        }
//
//
//
//        allCustomers.subscribe(System.out::println);


        List<Customer> customerList = customerData();
        customerRepository
                .deleteAll()
                .thenMany(Flux.fromIterable(customerList))
                .flatMap(x -> {

                    Mono<Customer> newlyAddedCustomer = customerRepository.save(x);
                    return newlyAddedCustomer;
                })
                .thenMany(customerRepository.findAll())
                .subscribe(x -> {
                    System.out.println(x);
                });

        addressRepo
                .deleteAll()
                .thenMany(Flux.fromIterable(addressData()))
                .flatMap(x -> {

                    Mono<CustomerAddress> newlyInsertedAddress = addressRepo.save(x);
                    return newlyInsertedAddress;
                }).thenMany(addressRepo.findAll())
                .subscribe(x -> {
                    System.out.println(x);
                });


    }

}
