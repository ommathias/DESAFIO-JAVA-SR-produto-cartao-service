package com.desafiotecnico.product_card_service.controller;


import com.desafiotecnico.product_card_service.builder.ClientBuilder;
import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.exception.DuplicateCpfException;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.record.ClientResponseDTO;
import com.desafiotecnico.product_card_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        ClientResponseDTO response = ClientBuilder.clientToResponseDTO(client);

        return ResponseEntity.status(200).body(response);
    }



    @ExceptionHandler(DuplicateCpfException.class)
    public ResponseEntity<String> handleDuplicateCpfException(DuplicateCpfException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }




}
