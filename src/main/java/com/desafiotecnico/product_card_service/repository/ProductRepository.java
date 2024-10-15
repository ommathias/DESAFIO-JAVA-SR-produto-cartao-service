package com.desafiotecnico.product_card_service.repository;

import com.desafiotecnico.product_card_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
