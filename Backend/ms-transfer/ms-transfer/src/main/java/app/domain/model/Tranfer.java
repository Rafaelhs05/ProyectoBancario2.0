package app.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfers")
public class Tranfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransfer;

    private String NumberTransfer;
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private LocalDateTime transferDate;
    private String description;

}
