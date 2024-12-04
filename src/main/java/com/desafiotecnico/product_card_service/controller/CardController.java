package com.desafiotecnico.product_card_service.controller;


import com.desafiotecnico.product_card_service.builder.CardBuilder;
import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.record.CardRecordCreate;
import com.desafiotecnico.product_card_service.record.CardResponseDTO;
import com.desafiotecnico.product_card_service.record.ProductResponseDTO;
import com.desafiotecnico.product_card_service.service.CardService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CardResponseDTO>> getAllCards(){
        List<Card> cards = cardService.getAllCards();

        if (cards.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }

        List<CardResponseDTO> cardsResponse = cards.stream().map(CardBuilder::CardResponseToDTO).collect(Collectors.toList());

        return ResponseEntity.ok(cardsResponse);

    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardRecordCreate cardRecord){

        //validações aqui
        Card newCard = cardService.createCard(cardRecord);
        CardResponseDTO response = new CardResponseDTO(
                newCard.getId(),
                newCard.getNumber(),
                newCard.getHolder(),
                newCard.getCreationDate()
        );

        return  ResponseEntity.status(201).body(response);
    }

}
