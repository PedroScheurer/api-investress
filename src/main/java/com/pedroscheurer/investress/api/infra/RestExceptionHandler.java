package com.pedroscheurer.investress.api.infra;

import com.pedroscheurer.investress.api.exceptions.BrapiResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BrapiResponseException.class)
    private ResponseEntity<RestErrorMessage> brapiResponseHandler(BrapiResponseException exception) {
        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(exception.getMessage().substring(0, 3)));

        RestErrorMessage treatedResponse = new RestErrorMessage("Erro em consultar API Brapi:"
                + exception.getMessage().substring(80), status);

        return ResponseEntity.status(status).body(treatedResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RestErrorMessage> runtimeExceptionHandler(RuntimeException exception) {

        RestErrorMessage treatedResponse = new RestErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(400).body(treatedResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<RestErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException exception) {

        RestErrorMessage treatedResponse = new RestErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(400).body(treatedResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNameNotFoundExceptionHandler(UsernameNotFoundException exception) {

        RestErrorMessage treatedResponse = new RestErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(400).body(treatedResponse);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();
        RestErrorMessage treatedResponse = new RestErrorMessage(errorMessage, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(400).body(treatedResponse);
    }
}
