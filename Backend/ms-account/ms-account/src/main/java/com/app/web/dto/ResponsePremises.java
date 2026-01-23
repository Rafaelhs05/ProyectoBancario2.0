package com.app.web.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePremises {


    private String city;

    private String location;

    private String code;

    private String state ;

}
