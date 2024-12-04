package com.desafiotecnico.product_card_service.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}