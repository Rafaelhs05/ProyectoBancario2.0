package com.app.application.mappper;

import com.app.application.client.Customer;
import com.app.domain.model.Account;
import com.app.domain.model.Premises;
import com.app.web.dto.RequestAccount;
import com.app.web.dto.ResponseAccount;
import com.app.web.dto.ResponsePremises;

public interface MapperAccount {

    public Account toAccount(RequestAccount requestAccount, Premises premises);

    public ResponseAccount toResponseAccount(Account account, Customer customer, ResponsePremises responsePremises);

    ResponsePremises toResponsePremises(Premises premises);

}
