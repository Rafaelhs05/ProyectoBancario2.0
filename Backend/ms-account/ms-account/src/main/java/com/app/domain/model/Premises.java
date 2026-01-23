package com.app.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "premises")
public class Premises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPremises;

    private String city;

    private String location;

    private String code;

    @Enumerated(EnumType.STRING)
    private StatePremises state;

}
