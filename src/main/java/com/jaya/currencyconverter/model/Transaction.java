package com.jaya.currencyconverter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String id;

    private Long userId;

    private String originCurrency;

    private BigDecimal originValue;

    private String targetCurrency;

    private BigDecimal rate;

    private LocalDateTime date;

    private BigDecimal targetValue;

}
