package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.builder.ClientBuilder;
import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.exception.DuplicateCpfException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.record.ClientRecordUpdate;
import com.desafiotecnico.product_card_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
@Slf4j

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
            throw new DataIntegrityViolationException("CPF already registered: " + clientRecordCreate.cpf());
        }


    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));

    }

    public Client updateClient(Long id, ClientRecordUpdate clientRecordUpdate) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with ID " + id + " not found"));

        try {
            Client updatedClient = ClientBuilder.updateClientRecordToClient(clientRecordUpdate, existingClient);
            return clientRepository.save(updatedClient);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("uk_client_cpf")) {
                throw new DuplicateCpfException("CPF '" + clientRecordUpdate.cpf() + "' already registered");
            } else {
                log.error("Integrity error:", ex);
                throw new RuntimeException("Error updating client", ex);

            }
        }
    }


}
