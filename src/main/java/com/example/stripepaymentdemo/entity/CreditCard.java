package com.example.stripepaymentdemo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String brand;
    String last4;
    Long expMonth;
    Long expYear;
    String paymentMethodId;
    @ManyToOne
    @JoinColumn(name = "card_user", nullable = false)
    User user;
}
