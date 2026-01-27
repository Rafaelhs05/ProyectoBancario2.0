package app.auth.application.service.imp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.auth.application.client.UserFeignClient;
import app.auth.application.mapper.MapperToken;
import app.auth.application.security.JwtProvider;
import app.auth.application.service.AuthService;
import app.auth.web.dto.ResponseUser;
import app.auth.web.dto.ResponseToken;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserFeignClient userFeignClient;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MapperToken mapperToken;

    @Override
    public ResponseToken login(String username, String password) {

        ResponseUser user = userFeignClient.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Usuario no existe");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("Usuario inactivo");
        }
        userFeignClient.updateLastLogin(username);
        return mapperToken.toResponseToken(jwtProvider.createToken(user));
    }
}
