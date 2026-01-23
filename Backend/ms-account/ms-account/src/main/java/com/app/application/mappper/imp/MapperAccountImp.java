package com.app.application.mappper.imp;

import com.app.domain.model.Premises;
import com.app.web.dto.ResponsePremises;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.app.application.client.Customer;
import com.app.application.mappper.MapperAccount;
import com.app.domain.model.Account;
import com.app.web.dto.RequestAccount;
import com.app.web.dto.ResponseAccount;

@Component
@RequiredArgsConstructor
public class MapperAccountImp implements MapperAccount {

    @Override
    public Account toAccount(RequestAccount requestAccount, Premises premises) {
        return Account.builder()
                //.accountNumber(requestAccount.getAccountNumber())
                //.accountNumberCCI(requestAccount.getAccountNumberCCI())
                .accountType(requestAccount.getAccountType())
                //.balance(requestAccount.getBalance())
                //.status(requestAccount.getStatus())
                .currency(requestAccount.getCurrency())
                //.apeningDate(requestAccount.getApeningDate())
                //.ownership(requestAccount.getOwnership())
                .idClient(requestAccount.getIdClient())
                .premises(premises)
                .build();
    }

    @Override
    public ResponseAccount toResponseAccount(Account account, Customer customer, ResponsePremises responsePremises) {

        return ResponseAccount.builder()
                .idAccount(account.getIdAccount())
                .accountNumber(account.getAccountNumber())
                .accountNumberCCI(account.getAccountNumberCCI())
                .accountType(account.getAccountType().toString())
                .balance(account.getBalance())
                .status(account.getStatus().toString())
                .currency(account.getCurrency())
                .apeningDate(account.getApeningDate())
                .ownership(customer.getName())
                .premises(responsePremises)
                .build();
    }

    @Override
    public ResponsePremises toResponsePremises(Premises premises) {
        return ResponsePremises.builder()
                .city(premises.getCity())
                .location(premises.getLocation())
                .code(premises.getCode())
                .state(premises.getState().toString())
                .build();
    }

}
