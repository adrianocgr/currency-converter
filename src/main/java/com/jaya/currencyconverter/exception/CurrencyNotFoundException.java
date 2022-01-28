package com.jaya.currencyconverter.exception;

import com.jaya.currencyconverter.CurrencyConverterApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends AbstractLoggerException {
    public CurrencyNotFoundException(String message){
        super(message);
    }
}

