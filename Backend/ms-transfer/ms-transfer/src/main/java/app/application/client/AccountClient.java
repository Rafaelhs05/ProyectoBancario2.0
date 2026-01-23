package app.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-account")
public interface AccountClient {

    @GetMapping("/accounts/accountNumber/{numberAccount}")
    AccountDto getAccount(@PathVariable String numberAccount);

    @PutMapping("/accounts/withdraw/{numberAccount}")
    AccountDto withdraw(@PathVariable String numberAccount, @RequestBody RequestAmount requestAmount);

    @PutMapping("/accounts/deposit/{numberAccount}")
    AccountDto deposit(@PathVariable String numberAccount, @RequestBody RequestAmount requestAmount);

}
