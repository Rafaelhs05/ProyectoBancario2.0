package app.application.mapper;

import app.domain.model.UserCusto;
import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;

public interface MapperUser {

    UserCusto toEntity(RequestUser requestUser);

    ResponseUser toResponse(UserCusto user);
}
