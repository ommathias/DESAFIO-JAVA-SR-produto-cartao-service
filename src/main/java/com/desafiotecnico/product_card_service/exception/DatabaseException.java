package com.desafiotecnico.product_card_service.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}