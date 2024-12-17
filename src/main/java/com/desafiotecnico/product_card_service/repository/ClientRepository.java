package com.desafiotecnico.product_card_service.repository;

import com.desafiotecnico.product_card_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
