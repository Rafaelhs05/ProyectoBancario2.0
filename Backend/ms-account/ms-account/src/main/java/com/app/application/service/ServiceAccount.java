package com.app.application.service;

import java.math.BigDecimal;
import java.util.List;
import com.app.domain.model.StateAccount;
import com.app.domain.model.StatePremises;
import com.app.web.dto.RequestAccount;
import com.app.web.dto.ResponseAccount;
import com.app.web.dto.ResponsePremises;

public interface ServiceAccount {

    ResponseAccount createAccount(RequestAccount requestAccount);

    ResponseAccount getAccountById(Long idAccount);

    List<ResponseAccount> getAllAccounts(Long idClient);

    ResponseAccount deposit(String numberAccount, BigDecimal amount);

    ResponseAccount withdraw(String numberAccount, BigDecimal amount);

    ResponseAccount blockAccount(String numberAccount);

    ResponseAccount unblockAccount(String numberAccount);

    ResponseAccount closeAccount(String numberAccount);

    List<ResponseAccount> getAccountsStatusActive(StateAccount status);

    ResponseAccount getAccountByNumber(String numberAccount);

    List<ResponsePremises> listPremises(StatePremises statePremises);

}
