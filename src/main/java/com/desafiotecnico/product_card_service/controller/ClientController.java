package com.desafiotecnico.product_card_service.controller;


import com.desafiotecnico.product_card_service.builder.ClientBuilder;
import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.exception.DuplicateCpfException;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.record.ClientRecordUpdate;
import com.desafiotecnico.product_card_service.record.ClientResponseDTO;
import com.desafiotecnico.product_card_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRecordCreate clientRecordCreate) {
        Client newClient = clientService.createClient(clientRecordCreate);
        ClientResponseDTO response = new ClientResponseDTO(
                newClient.getId(),
                newClient.getName(),
                newClient.getCpf(),
                newClient.getCreationDate()
        );

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {

        List<Client> clients = clientService.getAllClients();
        if(clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ClientResponseDTO> clientsResponse = clients.stream()
                .map(ClientBuilder::clientToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        ClientResponseDTO response = ClientBuilder.clientToResponseDTO(client);

        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody ClientRecordUpdate clientRecordUpdate)
    {
        Client updatedClient = clientService.updateClient(id, clientRecordUpdate);
        return ResponseEntity.status(200).body(updatedClient);
    }



    @ExceptionHandler(DuplicateCpfException.class)
    public ResponseEntity<String> handleDuplicateCpfException(DuplicateCpfException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }




}
