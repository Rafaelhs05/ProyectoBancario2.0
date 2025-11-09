package app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.model.Country;

public interface RepositoryCountry extends JpaRepository<Country, Long> {

    

}
