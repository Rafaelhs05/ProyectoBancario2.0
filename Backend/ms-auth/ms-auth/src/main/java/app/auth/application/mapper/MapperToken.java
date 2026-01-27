package app.auth.application.mapper;

import app.auth.web.dto.ResponseToken;

public interface MapperToken {

    ResponseToken toResponseToken(String token);

}
