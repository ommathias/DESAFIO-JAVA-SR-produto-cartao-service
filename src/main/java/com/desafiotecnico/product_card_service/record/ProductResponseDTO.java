package com.desafiotecnico.product_card_service.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ProductResponseDTO(Long id, String name, BigDecimal price, LocalDateTime creationDate) {
}