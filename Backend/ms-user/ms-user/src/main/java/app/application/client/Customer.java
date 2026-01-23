package app.application.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonAppend
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
