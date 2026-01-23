package app.application.service;

import app.web.dto.RequestTransfer;
import app.web.dto.ResponseTransfer;

public interface ServiceTransfer {

    ResponseTransfer createTransfer(RequestTransfer requestTransfer);

}
