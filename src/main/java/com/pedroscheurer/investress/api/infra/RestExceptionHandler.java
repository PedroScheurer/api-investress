package com.pedroscheurer.investress.api.infra;

import com.pedroscheurer.investress.api.exceptions.BrapiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BrapiResponseException.class)
    private ResponseEntity<String> brapiResponseHandler(BrapiResponseException exception){
        return ResponseEntity.status(Integer.parseInt(exception.getMessage().substring(0,3))).body("Erro em consultar API Brapi:"
        + exception.getMessage().substring(80));
    }
}
