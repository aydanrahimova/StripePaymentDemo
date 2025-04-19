package com.example.stripepaymentdemo.mapper;

import com.example.stripepaymentdemo.dto.request.UserRequest;
import com.example.stripepaymentdemo.dto.response.UserResponse;
import com.example.stripepaymentdemo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);

    UserResponse toDto(User user);
}
