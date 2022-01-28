package com.jaya.currencyconverter.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestWrapper {
    private String originCurrency;
    private String targetCurrency;
    private BigDecimal amount;
}
