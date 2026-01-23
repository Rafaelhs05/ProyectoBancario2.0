package app.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-motion")
public interface MotionClient {

    @PostMapping("/motion")
    void createMotion(@RequestBody MotionDto motionDto);

}
