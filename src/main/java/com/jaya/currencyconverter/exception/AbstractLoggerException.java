package com.jaya.currencyconverter.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLoggerException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLoggerException.class);

    protected AbstractLoggerException(String message){
        super(message);
        logger.error(message);
    }
}
