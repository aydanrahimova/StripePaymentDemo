package com.example.stripepaymentdemo.repository;

import com.example.stripepaymentdemo.entity.CreditCard;
import com.example.stripepaymentdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByUser(User user);
}
