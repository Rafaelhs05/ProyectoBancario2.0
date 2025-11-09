package app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.model.City;

public interface RepositoryCity extends JpaRepository<City, Long> {

}
