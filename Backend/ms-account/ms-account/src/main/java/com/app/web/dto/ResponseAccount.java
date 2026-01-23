package com.app.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class ResponseAccount {

    private Long idAccount;
    private String accountNumber;
    private String accountNumberCCI;
    private String accountType;
    private BigDecimal balance;
    private String status;
    private String currency;
    private LocalDate apeningDate;
    private String ownership;
    private ResponsePremises premises;
}
