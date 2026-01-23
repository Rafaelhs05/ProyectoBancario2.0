package app.web.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUser {

    private String username;
    private String password;
    private Long idClient;
}
