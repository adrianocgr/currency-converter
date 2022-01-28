package com.jaya.currencyconverter.controller;

import com.jaya.currencyconverter.model.Transaction;
import com.jaya.currencyconverter.service.TransactionService;
import com.jaya.currencyconverter.wrapper.TransactionRequestWrapper;
import com.jaya.currencyconverter.wrapper.TransactionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/converter/{userId}")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity convert(@PathVariable Long userId, @RequestBody TransactionRequestWrapper transactionReqWp){
        TransactionWrapper transactionWp = TransactionWrapper.builder()
                .originCurrency(transactionReqWp.getOriginCurrency())
                .targetCurrency(transactionReqWp.getTargetCurrency())
                .amount(transactionReqWp.getAmount())
                .build();
        return new ResponseEntity<Transaction>(transactionService.convert(userId, transactionWp), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity convert(@PathVariable Long userId){
        return new ResponseEntity<List<Transaction>>(transactionService.findByUserId(userId), HttpStatus.OK);
    }
}
