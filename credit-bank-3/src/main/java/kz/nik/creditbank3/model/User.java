package kz.nik.creditbank3.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "bank3")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "credit_quantity")
    private int creditQuantity;

    @Column(name = "credit_repaid")
    private int creditRepaid;

    @Column(name = "credit_sum")
    private int creditSum;

    @Column(name = "repaid_sum")
    private int repaidSum;
}