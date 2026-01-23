package app.gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private final Key secret;

    public JwtAuthenticationGatewayFilterFactory(@Value("${jwt.secret}") String secretString) {
        super(Config.class);

        if (secretString == null || secretString.isBlank()) {
            throw new IllegalStateException("JWT secret no configurado");
        }

        this.secret = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getPath().toString();
            String method = exchange.getRequest().getMethod().name();

            List<String> publicGetPaths = config.publicGetPaths;

            if ("GET".equalsIgnoreCase(method) && publicGetPaths.stream().anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No se proporcionó un token", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Authorization header inválido", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            if (!validateToken(token)) {
                return onError(exchange, "Token inválido", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }

    public static class Config {

        List<String> publicGetPaths = new ArrayList<>();

        public List<String> getPublicGetPaths() {

            return publicGetPaths;
        }

        public void setPublicGetPaths(List<String> publicGetPaths) {

            this.publicGetPaths = publicGetPaths;
        }
    }
}
