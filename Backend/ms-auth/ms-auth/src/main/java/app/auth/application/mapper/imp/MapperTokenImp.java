package app.auth.application.mapper.imp;

import org.springframework.stereotype.Component;

import app.auth.application.mapper.MapperToken;
import app.auth.web.dto.ResponseToken;

@Component
public class MapperTokenImp implements MapperToken {

    @Override
    public ResponseToken toResponseToken(String token) {

        return ResponseToken.builder()
                .token(token)
                .build();
    }

}
