package com.desafiotecnico.product_card_service.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record CardResponseDTO(Long id, String number, String holder, LocalDateTime creationDate) {
}