package app.application.mapper;

import app.domain.model.City;
import app.domain.model.Country;
import app.domain.model.Customer;
import app.web.dto.RequestCustomer;
import app.web.dto.ResponseCity;
import app.web.dto.ResponseCustomer;

public interface MapperCustomer {

    Customer toEntity(RequestCustomer requestCustomer, City city);

    ResponseCustomer toDto(Customer customer, ResponseCity cityDto);

    ResponseCity toDtoCity(City city , Country country);

}
