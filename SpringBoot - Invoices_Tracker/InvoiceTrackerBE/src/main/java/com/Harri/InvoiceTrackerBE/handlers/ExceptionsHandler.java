package com.Harri.InvoiceTrackerBE.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {UnsupportedOperationException.class})
    public ResponseEntity<Object> noUserFound(Exception exception){

        return new ResponseEntity<>("No User Found !",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> badCredentials(Exception exception){

        return new ResponseEntity<>("Invalid Credentials!",HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handle(Exception exception){

        return new ResponseEntity<>("Something Went Wrong !!!",HttpStatus.EXPECTATION_FAILED);
    }

}