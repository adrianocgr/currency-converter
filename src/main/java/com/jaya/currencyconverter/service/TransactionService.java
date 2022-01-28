package com.jaya.currencyconverter.service;

import com.jaya.currencyconverter.model.Transaction;
import com.jaya.currencyconverter.wrapper.TransactionWrapper;

import java.util.List;

public interface TransactionService {
    List<Transaction> findByUserId(Long userId);
    Transaction convert(Long userId, TransactionWrapper transactionWrapper);
}
