package app.auth.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import app.auth.web.dto.ResponseUser;

@FeignClient(name = "ms-user")
public interface UserFeignClient {

    @GetMapping("/user/username/{username}")
    ResponseUser findByUsername(@PathVariable("username") String username);

    @PutMapping("/user/updateLastLogin/{username}")
    ResponseUser updateLastLogin(@PathVariable("username") String username);
}
