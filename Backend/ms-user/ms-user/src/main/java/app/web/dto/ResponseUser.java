package app.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUser {

    private String username;
    private String password;
    private String role;
    private String status;
    private LocalDateTime lastLogin;
}
