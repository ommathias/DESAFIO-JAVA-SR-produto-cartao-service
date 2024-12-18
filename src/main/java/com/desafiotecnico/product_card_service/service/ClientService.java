package com.desafiotecnico.product_card_service.service;

import com.desafiotecnico.product_card_service.builder.ClientBuilder;
import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.exception.DuplicateCpfException;
import com.desafiotecnico.product_card_service.exception.NotFoundException;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.record.ClientRecordUpdate;
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
            throw new DataIntegrityViolationException("CPF already registered: " + clientRecordCreate.cpf());
        }


    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Record with id " + id + " not found."));

    }

    public Client updateClient(Long id, ClientRecordUpdate clientRecordUpdate) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente com ID " + id + " não encontrado"));

        try {
            Client updatedClient = ClientBuilder.updateClientRecordToClient(clientRecordUpdate, existingClient);
            return clientRepository.save(updatedClient);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("uk_client_cpf")) {
                throw new DuplicateCpfException("CPF '" + clientRecordUpdate.cpf() + "' já cadastrado");
            } else {
                log.error("Erro de integridade de dados:", ex);
                throw new RuntimeException("Erro ao atualizar cliente", ex);

            }
        }
    }


}
