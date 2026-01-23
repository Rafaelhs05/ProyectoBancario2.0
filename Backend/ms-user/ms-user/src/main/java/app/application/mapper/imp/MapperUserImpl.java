package app.application.mapper.imp;

import app.application.mapper.MapperUser;
import app.domain.model.UserCusto;
import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;

import org.springframework.stereotype.Component;

@Component
public class MapperUserImpl implements MapperUser {

    @Override
    public UserCusto toEntity(RequestUser requestUser) {
        return UserCusto.builder()
                .username(requestUser.getUsername())
                .password(requestUser.getPassword())
                .idClient(requestUser.getIdClient())
                .build();
    }

    @Override
    public ResponseUser toResponse(UserCusto user) {

        return ResponseUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .status(user.getStatus().toString())
                .lastLogin(user.getLastLogin())
                .build();
    }
}
