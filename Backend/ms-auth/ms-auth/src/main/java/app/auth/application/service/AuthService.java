package app.auth.application.service;

import app.auth.web.dto.ResponseToken;

public interface AuthService {

    ResponseToken login(String username, String password);

}
