package app.domain.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.model.Customer;

public interface RepositoryCustomer extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDocumentNumber(String documentNumber);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByIdCustomer(Long idCustomer);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    
    




}
