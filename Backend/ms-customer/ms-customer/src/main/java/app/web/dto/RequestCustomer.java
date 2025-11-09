package app.web.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCustomer {

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
    private Long cityId;

}
