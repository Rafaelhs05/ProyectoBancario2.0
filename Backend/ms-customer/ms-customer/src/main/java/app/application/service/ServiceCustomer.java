package app.application.service;

import java.util.List;

import app.web.dto.RequestCustomer;
import app.web.dto.ResponseCity;
import app.web.dto.ResponseCustomer;

public interface ServiceCustomer {

    ResponseCustomer nuevoCustomer(RequestCustomer requestCustomer);

    ResponseCustomer actualizarCustomer(RequestCustomer requestCustomer);

    ResponseCustomer eliminarCustomer(Long idCustomer);

    ResponseCustomer obtenerCustomerPorId(Long idCustomer);

    ResponseCustomer obtenerCustomerPorDni(String dni);

    List<ResponseCustomer> obtenerTodosLosCustomers();

    List<ResponseCity> obtenerTodasLasCiudades();

}
