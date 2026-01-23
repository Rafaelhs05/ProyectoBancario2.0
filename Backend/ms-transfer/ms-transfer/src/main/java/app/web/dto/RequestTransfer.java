package app.web.dto;

import java.math.BigDecimal;
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
public class RequestTransfer {

    private String NumberTransfer;
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    // private String currency;
    // private TransferStatus status;
    // private LocalDateTime transferDate;
    private String description;

}
