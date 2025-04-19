package com.example.stripepaymentdemo.repository;

import com.example.stripepaymentdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
