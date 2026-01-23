package app.auth.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {

    private String username;
    private String password;
    private String role;
    private String status;
    private LocalDateTime lastLogin;
}
