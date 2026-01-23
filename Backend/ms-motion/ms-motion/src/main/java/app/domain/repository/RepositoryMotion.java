package app.domain.repository;

import app.domain.model.Motion;
import app.domain.model.StatusMotion;
import app.domain.model.TypeMotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryMotion extends JpaRepository<Motion, Long> {

    boolean existsByIdMotion(Long idMotion);

    List<Motion> findByOriginAccount(String originAccount);

    List<Motion> findByStatus(StatusMotion status);

    List<Motion> findByMotionType(TypeMotion motionType);
}
