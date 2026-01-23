package app.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "motions")
public class Motion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMotion;

    @Enumerated(EnumType.STRING)
    private TypeMotion motionType;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private String currency;
    private LocalDateTime motionDate;
    private String originAccount;
    private String destinationAccount;

    @Enumerated(EnumType.STRING)
    private StatusMotion status;
}
