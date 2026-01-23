package app.application.mapper.imp;

import org.springframework.stereotype.Component;
import app.application.mapper.MapperCustomer;
import app.domain.model.City;
import app.domain.model.Country;
import app.domain.model.Customer;
import app.web.dto.RequestCustomer;
import app.web.dto.ResponseCity;
import app.web.dto.ResponseCustomer;

@Component
public class MapperCustomerImp implements MapperCustomer {

    @Override
    public Customer toEntity(RequestCustomer requestCustomer, City city) {

        return Customer.builder()
                .name(requestCustomer.getName())
                .lastName(requestCustomer.getLastName())
                .phoneNumber(requestCustomer.getPhoneNumber())
                .documentType(requestCustomer.getDocumentType())
                .documentNumber(requestCustomer.getDocumentNumber())
                .email(requestCustomer.getEmail())
                .address(requestCustomer.getAddress())
                .birthDate(requestCustomer.getBirthDate())
                .age(requestCustomer.getAge())
                .dateCreation(requestCustomer.getDateCreation())
                .status(requestCustomer.getStatus())
                .city(city)
                .build();
    }

    @Override
    public ResponseCustomer toDto(Customer customer, ResponseCity cityDto) {

        return ResponseCustomer.builder()
                .idCustomer(customer.getIdCustomer())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .documentType(customer.getDocumentType().toString())
                .documentNumber(customer.getDocumentNumber())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .birthDate(customer.getBirthDate())
                .age(customer.getAge())
                .dateCreation(customer.getDateCreation())
                .status(customer.getStatus().toString())
                .city(cityDto)
                .build();
    }

    @Override
    public ResponseCity toDtoCity(City city, Country country) {
        return ResponseCity.builder()
                .idCity(city.getIdCity())
                .name(city.getName())
                .countryName(country.getName())
                .build();
    }

}
