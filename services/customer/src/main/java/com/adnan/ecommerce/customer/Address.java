package com.adnan.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Builder
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipcode;
}
