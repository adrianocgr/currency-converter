package com.jaya.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmountNotInformedException extends AbstractLoggerException{
    public AmountNotInformedException(){
        super("Amount not informed.");
    }
}
