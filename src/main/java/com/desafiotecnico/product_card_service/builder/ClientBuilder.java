package com.desafiotecnico.product_card_service.builder;

import com.desafiotecnico.product_card_service.entity.Client;
import com.desafiotecnico.product_card_service.record.ClientRecordCreate;
import com.desafiotecnico.product_card_service.record.ClientRecordUpdate;
import com.desafiotecnico.product_card_service.record.ClientResponseDTO;

public class ClientBuilder {

    private ClientBuilder(){
        //impossibilita instanciação
    }


    //ClientRecordCreate to Client
    public static Client CLientRecordToClient(ClientRecordCreate request){
        return Client.builder()
                .name(request.name())
                .cpf(request.cpf())
                .build();

    }

    //Convert UpdateClientRecord to Client
    public static Client updateClientRecordToClient(ClientRecordUpdate request, Client existingClient){
        return Client.builder()
                .id(existingClient.getId())
                .name(request.name()!=null ? request.name() : existingClient.getName())
                .cpf(request.cpf()!=null ? request.cpf() : existingClient.getCpf())
                .creationDate(existingClient.getCreationDate())
                .updateDate(java.time.LocalDateTime.now())
                .build();

    }

    public static ClientResponseDTO clientToResponseDTO(Client client){
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getCreationDate());

    }
}
