package app.application.client;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

    private String accountNumber;
    private String accountNumberCCI;
    private String accountType;
    private BigDecimal balance;
    private String status;
    private String currency;
    private LocalDate apeningDate;
    private String ownership;
}
