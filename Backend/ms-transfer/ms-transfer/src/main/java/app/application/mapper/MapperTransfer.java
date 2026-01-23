package app.application.mapper;

import app.web.dto.RequestTransfer;
import app.web.dto.ResponseTransfer;
import app.domain.model.Tranfer;

public interface MapperTransfer {

    Tranfer toTranfer(RequestTransfer requestTransfer);

    ResponseTransfer toResponseTransfer(Tranfer tranfer);

}
