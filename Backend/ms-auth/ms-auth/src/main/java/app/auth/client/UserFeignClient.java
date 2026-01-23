package app.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.auth.model.AuthUser;

@FeignClient(name = "ms-user")
public interface UserFeignClient {

    @GetMapping("/user/username/{username}")
    AuthUser findByUsername(@PathVariable("username") String username);
}
