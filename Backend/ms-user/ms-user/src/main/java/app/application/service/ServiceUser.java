package app.application.service;

import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;

public interface ServiceUser {

    ResponseUser newUser(RequestUser requestUser);

    ResponseUser getUsernameById(Long id);

    ResponseUser getByUsername(String username);

}
