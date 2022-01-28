package com.jaya.currencyconverter.integration.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExchangerateApiKeyNotInformedException extends RuntimeException{

    public ExchangerateApiKeyNotInformedException(){
        super("EXCHANGERATES_BASE environment variable not informed.");
    }
}
