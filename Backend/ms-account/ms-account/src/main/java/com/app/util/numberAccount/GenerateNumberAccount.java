package com.app.util.numberAccount;

import com.app.domain.model.TypeAccount;

public interface GenerateNumberAccount {

    String generateAccountNumber(TypeAccount accountType, String currency, String city , String location);
    String generateCCI(String accountNumber);
}
