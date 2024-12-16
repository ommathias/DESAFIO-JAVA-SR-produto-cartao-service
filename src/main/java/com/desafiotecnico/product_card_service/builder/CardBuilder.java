package com.desafiotecnico.product_card_service.builder;

import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.record.CardRecordCreate;
import com.desafiotecnico.product_card_service.record.CardRecordUpdate;
import com.desafiotecnico.product_card_service.record.CardResponseDTO;

public class CardBuilder {

    private CardBuilder() {
    }

    // Convert CreateCardRecord to Card
    public static Card CardRecordToCard(CardRecordCreate request) {
        return Card.builder()
                .number(request.number())
                .holder(request.holder())
                .build();
    }

    // Convert UpdateCardRecord to Card
    public static Card UpdateCardRecordtoCard(CardRecordUpdate request, Card existingCard) {
        return Card.builder()
                .id(existingCard.getId())  // preserve ID from existingCard
                .number(request.number() != null ? request.number() : existingCard.getNumber())
                .holder(request.holder() != null ? request.holder() : existingCard.getHolder())
                .creationDate(existingCard.getCreationDate())
                .updateDate(java.time.LocalDateTime.now())
                .build();
    }

    // Card to CardResponseDTO
    public static CardResponseDTO CardToCardResponseDTO(Card product) {
        return new CardResponseDTO(
                product.getId(),
                product.getNumber(),
                product.getHolder(),
                product.getCreationDate()
        );
    }
}