package app.application.mapper.imp;

import app.web.dto.RequestTransfer;
import app.web.dto.ResponseTransfer;
import app.domain.model.Tranfer;

import org.springframework.stereotype.Component;

import app.application.mapper.MapperTransfer;

@Component
public class MapperTransferImp implements MapperTransfer {

    @Override
    public Tranfer toTranfer(RequestTransfer requestTransfer) {
        return Tranfer.builder()
                .NumberTransfer(requestTransfer.getNumberTransfer())
                .originAccount(requestTransfer.getOriginAccount())
                .destinationAccount(requestTransfer.getDestinationAccount())
                .amount(requestTransfer.getAmount())
                // .currency(requestTransfer.getCurrency())
                .description(requestTransfer.getDescription())
                .build();
    }

    @Override
    public ResponseTransfer toResponseTransfer(Tranfer tranfer) {
        return ResponseTransfer.builder()
                .NumberTransfer(tranfer.getNumberTransfer())
                .originAccount(tranfer.getOriginAccount())
                .destinationAccount(tranfer.getDestinationAccount())
                .amount(tranfer.getAmount())
                .currency(tranfer.getCurrency())
                .status(tranfer.getStatus())
                .transferDate(tranfer.getTransferDate())
                .description(tranfer.getDescription())
                .build();
    }

}
