package com.app.application.client;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private Long idCustomer;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String documentType;
    private String documentNumber;
    private String email;
    private String address;
    private LocalDate birthDate;
    private Long age;
    private LocalDate dateCreation;
    private String status;

}
