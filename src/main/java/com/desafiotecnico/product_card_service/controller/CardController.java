package com.desafiotecnico.product_card_service.controller;


import com.desafiotecnico.product_card_service.builder.CardBuilder;
import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.CardRecordCreate;
import com.desafiotecnico.product_card_service.record.CardRecordUpdate;
import com.desafiotecnico.product_card_service.record.CardResponseDTO;
import com.desafiotecnico.product_card_service.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;


    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getAllCards() {
        List<Card> cards = cardService.getAllCards();

        if (cards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CardResponseDTO> cardsResponse = cards.stream().map(CardBuilder::CardToCardResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(cardsResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getCardById(@PathVariable Long id)
    {
        Card card = cardService.getCardById(id);
        CardResponseDTO response = CardBuilder.CardToCardResponseDTO(card);

        return ResponseEntity.ok().body(response);
    }


    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardRecordCreate cardRecord) {

        Card newCard = cardService.createCard(cardRecord);
        CardResponseDTO response = new CardResponseDTO(
                newCard.getId(),
                newCard.getNumber(),
                newCard.getHolder(),
                newCard.getCreationDate()
        );

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @RequestBody CardRecordUpdate cardRecordUpdate) {
        Card updatedCard = cardService.updateCard(id, cardRecordUpdate);
        return ResponseEntity.ok(updatedCard);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Card> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
