package com.jaya.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionsNotFoundException extends AbstractLoggerException{
    public TransactionsNotFoundException(){
        super("Transactions not found.");
    }
}
