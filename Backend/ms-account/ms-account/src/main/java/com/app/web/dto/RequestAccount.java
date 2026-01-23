package com.app.web.dto;

import com.app.domain.model.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAccount {

    private TypeAccount accountType;
    private String currency;
    // private String ownership;
    private Long idClient;
    private Long idPremises;
}
