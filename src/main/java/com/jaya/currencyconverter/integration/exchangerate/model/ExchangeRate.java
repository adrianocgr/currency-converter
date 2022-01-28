package com.jaya.currencyconverter.integration.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private Boolean sucess;
    private Integer timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
    private RequestError error;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RequestError{
        private Integer code;
        private String type;
        private String info;
    }
}
