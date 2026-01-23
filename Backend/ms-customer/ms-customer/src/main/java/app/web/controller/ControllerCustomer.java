package app.web.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.application.service.ServiceCustomer;
import app.web.dto.RequestCustomer;
import app.web.dto.ResponseCity;
import app.web.dto.ResponseCustomer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class ControllerCustomer {

    private final ServiceCustomer serviceCustomer;

    @GetMapping()
    public ResponseEntity<List<ResponseCustomer>> listaCliente() {

        return ResponseEntity.ok().body(serviceCustomer.obtenerTodosLosCustomers());

    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ResponseCustomer> clientePorDni(@PathVariable String dni) {

        return ResponseEntity.ok().body(serviceCustomer.obtenerCustomerPorDni(dni));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCustomer> clientePorId(@PathVariable Long id) {

        return ResponseEntity.ok().body(serviceCustomer.obtenerCustomerPorId(id));

    }

    @GetMapping("/city")
    public ResponseEntity<List<ResponseCity>> ListaCiudades() {

        return ResponseEntity.ok().body(serviceCustomer.obtenerTodasLasCiudades());

    }

    @PostMapping()
    public ResponseEntity<ResponseCustomer> nuevoCliente(@RequestBody RequestCustomer requestCustomer) {

        return ResponseEntity.status(HttpStatus.SC_CREATED).body(serviceCustomer.nuevoCustomer(requestCustomer));

    }

    @PutMapping()
    public ResponseEntity<ResponseCustomer> actualizarCliente(@RequestBody RequestCustomer requestCustomer) {

        return ResponseEntity.ok().body(serviceCustomer.actualizarCustomer(requestCustomer));

    }

    @DeleteMapping("/{idcliente}")
    public ResponseEntity<ResponseCustomer> eliminarCliente(@PathVariable Long idCliente) {

        return ResponseEntity.ok().body(serviceCustomer.eliminarCustomer(idCliente));

    }

}
