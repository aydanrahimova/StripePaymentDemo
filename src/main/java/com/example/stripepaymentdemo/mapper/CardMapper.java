package com.example.stripepaymentdemo.mapper;

import com.example.stripepaymentdemo.dto.response.CardResponse;
import com.example.stripepaymentdemo.entity.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardResponse toDto(CreditCard creditCard);
}
