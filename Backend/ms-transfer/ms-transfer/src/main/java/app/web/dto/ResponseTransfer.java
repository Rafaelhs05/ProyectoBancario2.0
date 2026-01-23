package app.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import app.domain.model.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTransfer {

    private String NumberTransfer;
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String currency;
    private TransferStatus status;
    private LocalDateTime transferDate;
    private String description;

}
