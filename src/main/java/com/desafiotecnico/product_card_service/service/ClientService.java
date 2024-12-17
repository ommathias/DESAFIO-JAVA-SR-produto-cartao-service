package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.builder.ClientBuilder;
import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.repository.ClientRepository;
import com.desafiotecnico.product_card_service.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    //GetAll
    public List<Client> getAllClients(){

           return clientRepository.findAll();

    }

    public Client createClient (ClientRecordCreate clientRecordCreate){

        try{
            Client client = ClientBuilder.CLientRecordToClient(clientRecordCreate);
            return clientRepository.save(client);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("CPF already registered.");
        }


    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));

    }


}
