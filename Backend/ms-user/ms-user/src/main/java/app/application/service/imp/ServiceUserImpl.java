package app.application.service.imp;

import app.application.client.ClientCustomer;
import app.application.mapper.MapperUser;
import app.application.service.ServiceUser;
import app.domain.model.RolUser;
import app.domain.model.StatusUser;
import app.domain.model.UserCusto;
import app.domain.repository.RepositoryUser;
import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceUserImpl implements ServiceUser {

    private final MapperUser mapperUser;

    private final RepositoryUser repositoryUser;

    private final ClientCustomer clientCustomer;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUser newUser(RequestUser requestUser) {

        if (clientCustomer.getCustomerById(requestUser.getIdClient()) == null) {
            throw new RuntimeException("El cliente no existe");
        }

        if (repositoryUser.existsByUsername(requestUser.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        UserCusto user = mapperUser.toEntity(requestUser);
        user.setStatus(StatusUser.ACTIVE);
        user.setRole(RolUser.USER);
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        UserCusto registry = repositoryUser.save(user);

        return mapperUser.toResponse(registry);
    }

    @Override
    public ResponseUser getUsernameById(Long id) {

        UserCusto user = repositoryUser.findById(id).orElseThrow(
                () -> new RuntimeException("El usuario no existe"));

        return mapperUser.toResponse(user);

    }

    @Override
    public ResponseUser getByUsername(String username) {

        UserCusto user = repositoryUser.findByUsername(username).orElseThrow(
                () -> new RuntimeException("El usuario no existe"));

        return mapperUser.toResponse(user);
    }

    @Override
    public ResponseUser updateLastLogin(String username) {

        UserCusto user = repositoryUser.findByUsername(username).orElseThrow(
                () -> new RuntimeException("El usuario no existe"));

        user.setLastLogin(LocalDateTime.now());
        return mapperUser.toResponse(repositoryUser.save(user));
    }

}
