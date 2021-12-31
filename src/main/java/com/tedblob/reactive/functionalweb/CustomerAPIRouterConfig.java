package com.tedblob.reactive.functionalweb;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerAPIRouterConfig {


    @Bean
    public RouterFunction<ServerResponse> customerRoutes(CustomerAPIHandler handler) {

        return
                RouterFunctions
                        .route(RequestPredicates.GET("/f/customers"),
                                handler::handleGetAllCustomers)
                        .andRoute(RequestPredicates.GET("/f/customers/{customerId}"),
                                handler::handleGetCustomerById);


    }

}
