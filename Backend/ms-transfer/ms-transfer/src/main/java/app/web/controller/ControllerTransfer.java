package app.web.controller;

import lombok.RequiredArgsConstructor;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.application.service.ServiceTransfer;
import app.web.dto.RequestTransfer;
import app.web.dto.ResponseTransfer;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class ControllerTransfer {

    private final ServiceTransfer serviceTransfer;

    @PostMapping
    public ResponseEntity<ResponseTransfer> createTransfer(@RequestBody RequestTransfer requestTransfer) {

        return ResponseEntity.status(HttpStatus.SC_CREATED).body(serviceTransfer.createTransfer(requestTransfer));
    }

}
