package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.exception.DatabaseException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.CardRecordCreate;
import com.desafiotecnico.product_card_service.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desafiotecnico.product_card_service.builder.CardBuilder;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {

        try {
            return cardRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Error fetching records from database");
        }
    }

    public Card getCardById(Long id) {
        try {
            return cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));
        } catch (Exception e) {
            throw new DatabaseException("Error fetching record from the database.");
        }
    }

    public Card createCard(CardRecordCreate cardRecord) {

        Card card = CardBuilder.CardRecordToCard(cardRecord);
        try{
            return cardRepository.save(card);
        }catch (Exception e){
            throw new DatabaseException("Error creating card.");
        }


    }

    public void deleteCard(long ID) {
        cardRepository.deleteById(ID);
    }
}
