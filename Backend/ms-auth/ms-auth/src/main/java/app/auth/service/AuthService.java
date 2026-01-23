package app.auth.service;

import app.auth.client.UserFeignClient;
import app.auth.model.AuthUser;
import app.auth.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFeignClient userFeignClient;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String username, String password) {

        AuthUser user = userFeignClient.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Usuario no existe");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("Usuario inactivo");
        }

        return jwtProvider.createToken(user);
    }
}
