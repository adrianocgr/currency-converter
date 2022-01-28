package com.jaya.currencyconverter.exception;

import com.jaya.currencyconverter.CurrencyConverterApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLoggerException extends RuntimeException {
    private static Logger logger = LoggerFactory.getLogger(CurrencyConverterApplication.class);

    public AbstractLoggerException(String message){
        super(message);
        logger.error(message);
    }
}
