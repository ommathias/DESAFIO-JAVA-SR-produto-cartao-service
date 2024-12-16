package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.exception.DatabaseException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.CardRecordCreate;
import com.desafiotecnico.product_card_service.record.CardRecordUpdate;
import com.desafiotecnico.product_card_service.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

            return cardRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));

    }

    public Card createCard(CardRecordCreate cardRecord) {

        Card card = CardBuilder.CardRecordToCard(cardRecord);
        try{
            return cardRepository.save(card);
        }catch (Exception e){
            throw new DatabaseException("Error creating card.");
        }


    }

    public void deleteCard(long id) {

        Card deletingCard = getCardById(id);
        if(deletingCard == null){
            throw new NotFoundException("Record with id " + id + " not found.");

        }

        cardRepository.delete(deletingCard);

    }

    public Card updateCard(Long id, CardRecordUpdate cardDetails) {
        try{
            Card existingCard = cardRepository.findById(id)
                    .orElseThrow(()->new NotFoundException("Card with ID: " + id + " not found."));
            Card updatedCard = CardBuilder.UpdateCardRecordtoCard(cardDetails, existingCard);
            return cardRepository.save(updatedCard);
        }catch (DataAccessException e){
            throw new DatabaseException("Error updating record to the database.");
        }
    }
}
