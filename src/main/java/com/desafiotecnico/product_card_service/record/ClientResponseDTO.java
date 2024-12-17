package com.desafiotecnico.product_card_service.record;

import java.time.LocalDateTime;

public record ClientResponseDTO (Long id, String name, String cpf, LocalDateTime creationDate){
}
