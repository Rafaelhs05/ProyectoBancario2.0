package com.app.web.controller;

import com.app.domain.model.StateAccount;
import com.app.domain.model.StatePremises;
import com.app.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.app.application.service.ServiceAccount;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class ControllerAccounts {

    private final ServiceAccount serviceAccount;

    @GetMapping("/client/{idClient}")
    public ResponseEntity<List<ResponseAccount>> ListAccounts(@PathVariable Long idClient) {

        return ResponseEntity.ok().body(serviceAccount.getAllAccounts(idClient));

    }

    // modificado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ResponseAccount>> ListAccounts(@PathVariable StateAccount status) {
        return ResponseEntity.ok().body(serviceAccount.getAccountsStatusActive(status));
    }

    @GetMapping("/{idAccount}")
    public ResponseEntity<ResponseAccount> getAccountById(@PathVariable Long idAccount) {

        return ResponseEntity.ok().body(serviceAccount.getAccountById(idAccount));

    }

    @GetMapping("/accountNumber/{numberAccount}")
    public ResponseEntity<ResponseAccount> getAccountByNumber(@PathVariable String numberAccount) {

        return ResponseEntity.ok().body(serviceAccount.getAccountByNumber(numberAccount));
    }

    @GetMapping
    public ResponseEntity<List<ResponsePremises>> listPremises(@RequestParam StatePremises statePremises) {

        return ResponseEntity.ok().body(serviceAccount.listPremises(statePremises));

    }

    @PostMapping
    public ResponseEntity<ResponseAccount> createAccount(@RequestBody RequestAccount requestAccount) {

        return ResponseEntity.status(HttpStatus.CREATED).body(serviceAccount.createAccount(requestAccount));
    }

    @PutMapping("/deposit/{numberAccount}")
    public ResponseEntity<ResponseAccount> deposit(@PathVariable String numberAccount,
            @RequestBody DepositRequest depositRequest) {

        return ResponseEntity.ok().body(serviceAccount.deposit(numberAccount, depositRequest.getAmount()));
    }

    @PutMapping("/withdraw/{numberAccount}")
    public ResponseEntity<ResponseAccount> withdraw(@PathVariable String numberAccount,
            @RequestBody WithdrawRequest withdrawRequest) {

        return ResponseEntity.ok().body(serviceAccount.withdraw(numberAccount, withdrawRequest.getAmount()));
    }

    @PutMapping("/unblockAccount/{numberAccount}")
    public ResponseEntity<ResponseAccount> unblockAccount(@PathVariable String numberAccount) {

        return ResponseEntity.ok().body(serviceAccount.blockAccount(numberAccount));
    }

    @PutMapping("/closeAccount/{numberAccount}")
    public ResponseEntity<ResponseAccount> closeAccount(@PathVariable String numberAccount) {

        return ResponseEntity.ok().body(serviceAccount.closeAccount(numberAccount));
    }

}
