package app.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// @org.springframework.cloud.openfeign.EnableFeignClients(basePackages =
// "com.auth.client")
// @org.springframework.context.annotation.ComponentScan(basePackages = {
// "app.auth", "com.auth" })
@EnableFeignClients
public class MsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthApplication.class, args);
	}

}
