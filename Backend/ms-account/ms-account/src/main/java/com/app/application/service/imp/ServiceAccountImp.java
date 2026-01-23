package com.app.application.service.imp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.domain.model.Premises;
import com.app.domain.model.StateAccount;
import com.app.domain.model.StatePremises;
import com.app.domain.repository.RepositoryPremises;
import com.app.util.numberAccount.GenerateNumberAccount;
import com.app.util.numberAccount.imp.GenerateNumberAccountImp;
import com.app.web.dto.ResponsePremises;
import org.springframework.stereotype.Service;
import com.app.application.client.Customer;
import com.app.application.client.CustomerClient;
import com.app.application.mappper.MapperAccount;
import com.app.application.service.ServiceAccount;
import com.app.domain.model.Account;
import com.app.domain.repository.ReposistoryAccount;
import com.app.web.dto.RequestAccount;
import com.app.web.dto.ResponseAccount;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceAccountImp implements ServiceAccount {

    private final ReposistoryAccount reposistoryAccount;

    private final MapperAccount mapperAccount;

    private final CustomerClient customerClient;

    private final GenerateNumberAccount generateNumberAccount;

    private final RepositoryPremises repositoryPremises;

    @Override
    public ResponseAccount createAccount(RequestAccount requestAccount) {

        Customer customer = customerClient.getCustomerById(requestAccount.getIdClient());

        if (customer == null) {
            throw new IllegalArgumentException("El cliente no existe");

        }
        // generar numero de cuenta
        GenerateNumberAccountImp.AccountNumbers numeroCuenta = generateUniqueBBVANumbers(requestAccount);

        Premises premise = repositoryPremises.findById(requestAccount.getIdPremises()).orElseThrow(
                () -> new RuntimeException("El local no existe"));

        Account newAccount = mapperAccount.toAccount(requestAccount, premise);
        newAccount.setAccountNumber(numeroCuenta.getAccountNumber());
        newAccount.setAccountNumberCCI(numeroCuenta.getCCI());
        newAccount.setBalance(BigDecimal.ZERO);
        newAccount.setStatus(StateAccount.ACTIVO);
        newAccount.setOwnership(customer.getName());
        newAccount.setApeningDate(LocalDate.now());
        Account registrarCuenta = reposistoryAccount.save(newAccount);

        ResponsePremises dto = mapperAccount.toResponsePremises(premise);

        return mapperAccount.toResponseAccount(registrarCuenta, customer, dto);
    }

    @Override
    public ResponseAccount getAccountById(Long idAccount) {

        Account account = reposistoryAccount.findById(idAccount).orElseThrow(
                () -> new RuntimeException("Cuenta no encontrada"));

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("Local no encontado"));

        Customer customer = customerClient.getCustomerById(account.getIdClient());

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);

        return mapperAccount.toResponseAccount(account, customer, dto);
    }

    @Override
    public List<ResponseAccount> getAllAccounts(Long idClient) {

        List<Account> accounts = reposistoryAccount.findAll();

        List<ResponseAccount> responseAccounts = new ArrayList<>();

        for (Account account : accounts) {
            Customer customer = customerClient.getCustomerById(account.getIdClient());

            Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                    () -> new RuntimeException("local no existe"));

            ResponsePremises dto = mapperAccount.toResponsePremises(premises);

            responseAccounts.add(mapperAccount.toResponseAccount(account, customer, dto));
        }

        return responseAccounts;
    }

    @Override
    public ResponseAccount deposit(String numberAccount, BigDecimal amount) {

        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        account.setBalance(account.getBalance().add(amount));
        Account accountUpdate = reposistoryAccount.save(account);

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);

        Customer customer = customerClient.getCustomerById(accountUpdate.getIdClient());

        return mapperAccount.toResponseAccount(accountUpdate, customer, dto);
    }

    @Override
    public ResponseAccount withdraw(String numberAccount, BigDecimal amount) {

        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");

        }

        account.setBalance(account.getBalance().subtract(amount));
        Account accountUpdate = reposistoryAccount.save(account);

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);
        Customer customer = customerClient.getCustomerById(accountUpdate.getIdClient());
        return mapperAccount.toResponseAccount(accountUpdate, customer, dto);
    }

    @Override
    public ResponseAccount blockAccount(String numberAccount) {

        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        account.setStatus(StateAccount.BLOQUEADO);
        Account accountUpdate = reposistoryAccount.save(account);
        Customer customer = customerClient.getCustomerById(accountUpdate.getIdClient());

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);
        return mapperAccount.toResponseAccount(accountUpdate, customer, dto);
    }

    @Override
    public ResponseAccount unblockAccount(String numberAccount) {

        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        account.setStatus(StateAccount.ACTIVO);
        Account accountUpdate = reposistoryAccount.save(account);
        Customer customer = customerClient.getCustomerById(accountUpdate.getIdClient());

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);
        return mapperAccount.toResponseAccount(accountUpdate, customer, dto);
    }

    @Override
    public ResponseAccount closeAccount(String numberAccount) {

        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        account.setStatus(StateAccount.CANCELADO);
        Account accountUpdate = reposistoryAccount.save(account);
        Customer customer = customerClient.getCustomerById(accountUpdate.getIdClient());
        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);
        return mapperAccount.toResponseAccount(accountUpdate, customer, dto);
    }

    @Override
    public List<ResponseAccount> getAccountsStatusActive(StateAccount status) {

        List<Account> accounts = reposistoryAccount.findByStatus(status);
        List<ResponseAccount> responseAccounts = new ArrayList<>();

        for (Account account : accounts) {

            Customer customer = customerClient.getCustomerById(account.getIdClient());

            Premises premises = repositoryPremises.findById(
                    account.getPremises().getIdPremises()).orElseThrow(() -> new RuntimeException("local no existe"));

            ResponsePremises dto = mapperAccount.toResponsePremises(premises);
            responseAccounts.add(mapperAccount.toResponseAccount(account, customer, dto));
        }

        return responseAccounts;
    }

    @Override
    public ResponseAccount getAccountByNumber(String numberAccount) {
        Account account = reposistoryAccount.findByAccountNumber(numberAccount).orElseThrow(
                () -> new IllegalArgumentException("La cuenta no existe"));

        Premises premises = repositoryPremises.findById(account.getPremises().getIdPremises()).orElseThrow(
                () -> new RuntimeException("local no existe"));

        Customer customer = customerClient.getCustomerById(account.getIdClient());

        ResponsePremises dto = mapperAccount.toResponsePremises(premises);
        return mapperAccount.toResponseAccount(account, customer, dto);
    }

    @Override
    public List<ResponsePremises> listPremises(StatePremises statePremises) {
        return repositoryPremises.findByState(statePremises)
                .stream()
                .map(mapperAccount::toResponsePremises)
                .toList();
    }

    private GenerateNumberAccountImp.AccountNumbers generateUniqueBBVANumbers(
            RequestAccount request) {

        int maxAttempts = 10;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {

                Premises premises = repositoryPremises.findById(request.getIdPremises()).orElseThrow(
                        () -> new RuntimeException("Local no encontrado"));

                // Generar número de cuenta usando la interfaz inyectada
                String accountNumber = generateNumberAccount.generateAccountNumber(
                        request.getAccountType(),
                        request.getCurrency(),
                        premises.getCity(),
                        premises.getLocation());

                // Verificar unicidad
                if (!reposistoryAccount.existsByAccountNumber(accountNumber)) {
                    // Generar CCI para este número
                    String cci = generateNumberAccount.generateCCI(accountNumber);

                    // Retornar objeto con ambos números
                    return new GenerateNumberAccountImp.AccountNumbers(accountNumber, cci);
                }

                // Si existe, reintentar
                if (attempt == maxAttempts) {
                    throw new RuntimeException(
                            "No se pudo generar número único después de " + maxAttempts + " intentos");
                }

                // Pequeña pausa
                Thread.sleep(100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Generación interrumpida");
            }
        }

        throw new RuntimeException("Error generando números de cuenta");
    }

}
