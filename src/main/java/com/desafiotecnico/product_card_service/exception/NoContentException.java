package com.desafiotecnico.product_card_service.exception;

public class NoContentException extends NotFoundException{
    public NoContentException(String message) {
        super(message);
    }
}
