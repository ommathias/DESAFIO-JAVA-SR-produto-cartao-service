package com.desafiotecnico.product_card_service.repository;

import com.desafiotecnico.product_card_service.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
