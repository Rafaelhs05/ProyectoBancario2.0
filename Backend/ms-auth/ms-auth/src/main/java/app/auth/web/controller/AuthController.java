package app.auth.web.controller;

import app.auth.application.service.AuthService;
import app.auth.web.dto.RequestLogin;
import app.auth.web.dto.ResponseToken;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> login(@RequestBody RequestLogin request) {

        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(authService.login(request.getUsername(), request.getPassword()));
    }
}
