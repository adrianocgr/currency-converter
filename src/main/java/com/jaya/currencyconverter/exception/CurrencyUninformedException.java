package com.jaya.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyUninformedException extends AbstractLoggerException {
    public CurrencyUninformedException(String message){
        super(message);
    }
}
