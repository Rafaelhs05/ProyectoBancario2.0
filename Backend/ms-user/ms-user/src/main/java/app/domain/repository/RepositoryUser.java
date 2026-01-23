package app.domain.repository;

import app.domain.model.UserCusto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepositoryUser extends JpaRepository<UserCusto, Long> {

    Optional<UserCusto> findByUsername(String username);

    boolean existsByUsername(String username);

}
