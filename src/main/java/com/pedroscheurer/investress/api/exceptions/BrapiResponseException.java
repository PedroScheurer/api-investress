package com.pedroscheurer.investress.api.exceptions;

public class BrapiResponseException extends RuntimeException {

    public BrapiResponseException() {
        super("Erro na requisiçao para API da Brapi");
    }

    public BrapiResponseException(String message) {
        super(message);
    }
}
