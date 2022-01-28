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
public class TransactionWrapper {
    private String originCurrency;
    private BigDecimal originRate;
    private String targetCurrency;
    private BigDecimal targetRate;
    private BigDecimal amount;
}
