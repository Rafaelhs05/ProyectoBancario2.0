package app.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_city")
    private City city;

}
