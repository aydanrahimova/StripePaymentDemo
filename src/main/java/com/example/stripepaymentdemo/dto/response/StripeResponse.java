package com.example.stripepaymentdemo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeResponse {
    String message;
    String status;
    String sessionId;
    String sessionUrl;
}
