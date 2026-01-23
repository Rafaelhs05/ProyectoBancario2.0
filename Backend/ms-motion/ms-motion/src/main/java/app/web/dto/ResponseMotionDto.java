package app.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMotionDto {

    private String motionType;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime motionDate;
    private String originAccount;
    private String destinationAccount;
    private String status;
}
