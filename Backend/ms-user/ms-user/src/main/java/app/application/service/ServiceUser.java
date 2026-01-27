package app.application.service;

import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;

public interface ServiceUser {

    ResponseUser newUser(RequestUser requestUser);

    ResponseUser updateLastLogin(String username);

    ResponseUser getUsernameById(Long id);

    ResponseUser getByUsername(String username);

}
