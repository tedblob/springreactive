package com.tedblob.reactive.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CustomerAddress {

    private String customerAddressId;

    private String customerId;
    private String line1;
    private String line2;
    private String city;
    private String type;
}
