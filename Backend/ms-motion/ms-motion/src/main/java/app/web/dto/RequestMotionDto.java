package app.web.dto;

import app.domain.model.TypeMotion;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestMotionDto {

    private TypeMotion motionType;
    private BigDecimal amount;
    private String currency;
    private String originAccount;
    private String destinationAccount;

}
