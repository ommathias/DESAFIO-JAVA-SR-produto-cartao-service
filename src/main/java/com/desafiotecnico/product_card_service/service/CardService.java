package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.entity.Card;
import com.desafiotecnico.product_card_service.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository; //Banco

    public List<Card> getAllCartoes(){
        return cardRepository.findAll();
    }

    public Card getCard(Long id){
        return cardRepository.findById(id).orElse(null);
    }

    public Card createCard(Card card){
        return cardRepository.save(card);
    }

    public void deleteCard(long ID){
        cardRepository.deleteById(ID);
    }
}
