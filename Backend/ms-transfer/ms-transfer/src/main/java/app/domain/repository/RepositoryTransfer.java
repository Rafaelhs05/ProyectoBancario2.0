package app.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import app.domain.model.Tranfer;
import app.domain.model.TransferStatus;

public interface RepositoryTransfer extends JpaRepository<Tranfer, Long> {

    Optional<Tranfer> findByIdTransfer(Long idTransfer);

    Optional<Tranfer> findByNumberTransfer(String numberTransfer);

    List<Tranfer> findByStatus(TransferStatus status);

}
