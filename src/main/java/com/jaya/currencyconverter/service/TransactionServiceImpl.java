package com.jaya.currencyconverter.service;

import com.jaya.currencyconverter.exception.AmountNotInformedException;
import com.jaya.currencyconverter.exception.CurrencyNotFoundException;
import com.jaya.currencyconverter.exception.CurrencyUninformedException;
import com.jaya.currencyconverter.exception.TransactionsNotFoundException;
import com.jaya.currencyconverter.integration.exchangerate.model.ExchangeRate;
import com.jaya.currencyconverter.integration.exchangerate.service.ExchangeRateService;
import com.jaya.currencyconverter.model.Transaction;
import com.jaya.currencyconverter.repository.TransactionRepository;
import com.jaya.currencyconverter.wrapper.TransactionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public List<Transaction> findByUserId(Long userId) {
        List<Transaction> results = repository.findByUserId(userId);

        if(results == null || results.isEmpty())
            throw new TransactionsNotFoundException();

        return results;
    }

    @Override
    public Transaction convert(Long userId, TransactionWrapper transactionWp){
        ExchangeRate exchangeRate = exchangeRateService.get();
        validateTransaction(transactionWp, exchangeRate);
        setRates(transactionWp, exchangeRate);
        Transaction transaction  = Transaction.builder()
                .userId(userId)
                .originCurrency(transactionWp.getOriginCurrency())
                .originValue(transactionWp.getAmount())
                .targetCurrency(transactionWp.getTargetCurrency())
                .rate(transactionWp.getTargetRate())
                .date(LocalDateTime.now())
                .build();

        calculate(transaction, transactionWp);
        return repository.save(transaction);
    }

    private void validateTransaction(TransactionWrapper transactionWp, ExchangeRate exchangeRate){
        if(transactionWp.getAmount() == null || transactionWp.getAmount().compareTo(BigDecimal.ZERO) == 0)
            throw new AmountNotInformedException();

        if(transactionWp.getOriginCurrency() == null || transactionWp.getOriginCurrency().isEmpty())
            throw new CurrencyUninformedException("Origin currency not informed.");

        if(transactionWp.getTargetCurrency() == null || transactionWp.getTargetCurrency().isEmpty())
            throw new CurrencyUninformedException("Destination currency not informed.");

        BigDecimal originRate = exchangeRate.getRates().get(transactionWp.getOriginCurrency());
        if(originRate == null)
            throw new CurrencyNotFoundException("Source currency not found.");

        BigDecimal targetRate = exchangeRate.getRates().get(transactionWp.getTargetCurrency());
        if(targetRate == null)
            throw new CurrencyNotFoundException("Destination currency not found.");
    }

    private void calculate(Transaction transaction, TransactionWrapper transactionWp){
        BigDecimal originRateValue = transactionWp.getOriginRate();
        BigDecimal targetRateValue = transactionWp.getTargetRate();
        BigDecimal amountInEUR = transactionWp.getAmount().divide(originRateValue, 6, RoundingMode.HALF_UP);
        BigDecimal result = amountInEUR.multiply(targetRateValue);
        transaction.setTargetValue(result);
    }

    private void setRates(TransactionWrapper transactionWp, ExchangeRate exchangeRate){
        transactionWp.setOriginRate(getRate(transactionWp.getOriginCurrency(), exchangeRate));
        transactionWp.setTargetRate(getRate(transactionWp.getTargetCurrency(), exchangeRate));
    }

    private BigDecimal getRate(String currency, ExchangeRate exchangeRate){
        return exchangeRate.getRates().get(currency);
    }
}
