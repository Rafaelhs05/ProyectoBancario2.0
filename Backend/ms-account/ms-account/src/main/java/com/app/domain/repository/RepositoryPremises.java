package com.app.domain.repository;

import com.app.domain.model.Premises;
import com.app.domain.model.StatePremises;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryPremises extends JpaRepository<Premises, Long> {

    Premises findByCity(String city);

    Premises findByLocation(String location);

    boolean existsByLocation(String Location);

    boolean existsByCode(String code);

    Optional<Premises> findByCityAndLocation(String city, String location);

    List<Premises> findByState(StatePremises state);
}
