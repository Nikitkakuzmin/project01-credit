package kz.nik.creditcreditsservice.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "credits")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "max_amount")
    private int maxAmount;
    @Column(name = "rate")
    private double rate;


}
