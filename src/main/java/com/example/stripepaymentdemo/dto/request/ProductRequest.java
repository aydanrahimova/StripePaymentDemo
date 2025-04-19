package com.example.stripepaymentdemo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String productName;
    Long amount;
    Long quantity;
    String currency;
}
