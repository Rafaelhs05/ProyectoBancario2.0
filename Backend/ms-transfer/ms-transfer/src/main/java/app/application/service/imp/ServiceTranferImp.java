package app.application.service.imp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import app.application.client.AccountClient;
import app.application.client.AccountDto;
import app.application.client.MotionClient;
import app.application.client.MotionDto;
import app.application.client.MotionType;
import app.application.client.RequestAmount;
import app.application.mapper.MapperTransfer;
import app.application.service.ServiceTransfer;
import app.domain.model.Tranfer;
import app.domain.model.TransferStatus;
import app.domain.repository.RepositoryTransfer;
import app.web.dto.RequestTransfer;
import app.web.dto.ResponseTransfer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceTranferImp implements ServiceTransfer {

    private final RepositoryTransfer repositoryTransfer;
    private final MapperTransfer mapperTransfer;
    private final AccountClient accountClient;
    private final MotionClient motionClient;

    @Override
    public ResponseTransfer createTransfer(RequestTransfer requestTransfer) {

        AccountDto origin = accountClient.getAccount(requestTransfer.getOriginAccount());
        AccountDto destination = accountClient.getAccount(requestTransfer.getDestinationAccount());

        if (origin == null || destination == null)
            throw new RuntimeException("Error al obtener la cuenta");

        if (origin.getBalance().compareTo(requestTransfer.getAmount()) < 0)
            throw new RuntimeException("Saldo insuficiente");

        if (!origin.getCurrency().equals(destination.getCurrency()))
            throw new RuntimeException("Monedas diferentes");

        Tranfer transfer = mapperTransfer.toTranfer(requestTransfer);
        transfer.setStatus(TransferStatus.PENDING);
        transfer.setCurrency(origin.getCurrency());
        transfer.setTransferDate(LocalDateTime.now());
        transfer = repositoryTransfer.save(transfer);

        RequestAmount amount = new RequestAmount(requestTransfer.getAmount());

        try {

            accountClient.withdraw(origin.getAccountNumber(), amount);
            accountClient.deposit(destination.getAccountNumber(), amount);

            motionClient.createMotion(
                    MotionDto.builder()
                            .motionType(MotionType.TRANSFERENCIA_OUT)
                            .amount(amount.getAmount())
                            .currency(origin.getCurrency())
                            .originAccount(origin.getAccountNumber())
                            .destinationAccount(destination.getAccountNumber())
                            .build());

            motionClient.createMotion(
                    MotionDto.builder()
                            .motionType(MotionType.TRANSFERENCIA_IN)
                            .amount(amount.getAmount())
                            .currency(destination.getCurrency())
                            .originAccount(origin.getAccountNumber())
                            .destinationAccount(destination.getAccountNumber())
                            .build());

            transfer.setStatus(TransferStatus.COMPLETED);

        } catch (Exception e) {
            transfer.setStatus(TransferStatus.FAILED);
            repositoryTransfer.save(transfer);
            throw new RuntimeException("Transfer failed");
        }

        return mapperTransfer.toResponseTransfer(repositoryTransfer.save(transfer));
    }

}
