package com.example.stripepaymentdemo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardResponse {
    Long id;
    String last4;
    String brand;
    Long expMonth;
    Long expYear;
    String paymentMethodId;
}
