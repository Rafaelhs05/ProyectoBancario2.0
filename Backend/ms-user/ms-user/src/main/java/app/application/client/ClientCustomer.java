package app.application.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-customer")
public interface ClientCustomer {

    @GetMapping("/customer/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);
}
