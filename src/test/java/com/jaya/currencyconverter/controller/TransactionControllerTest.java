package com.jaya.currencyconverter.controller;

import com.google.gson.Gson;
import com.jaya.currencyconverter.exception.AmountNotInformedException;
import com.jaya.currencyconverter.exception.CurrencyNotFoundException;
import com.jaya.currencyconverter.exception.CurrencyUninformedException;
import com.jaya.currencyconverter.exception.TransactionsNotFoundException;
import com.jaya.currencyconverter.model.Transaction;
import com.jaya.currencyconverter.service.TransactionService;
import com.jaya.currencyconverter.wrapper.TransactionRequestWrapper;
import com.jaya.currencyconverter.wrapper.TransactionWrapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

@WebMvcTest
class TransactionControllerTest {

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.transactionController);
    }

    @Test
    void deveRetornarSucesso_QuandoBuscarTransacoes() {
        when(this.transactionService.findByUserId(1L)).thenReturn(
                new ArrayList<>(Arrays.asList(
                        Transaction.builder()
                                .id("ABC")
                                .date(LocalDateTime.now())
                                .originCurrency("EUR")
                                .originValue(BigDecimal.ONE)
                                .rate(BigDecimal.ONE)
                                .targetCurrency("BRL")
                                .userId(1L)
                                .targetValue(BigDecimal.ONE)
                                .build()
                ))
        );

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/converter/{userId}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarNaoEncontrado_QuandoBuscarTransacoes() {
        when(this.transactionService.findByUserId(1L)).thenThrow(TransactionsNotFoundException.class);
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/converter/{userId}", 1L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarSucesso_QuandoEnviarUmaTransacao(){

        when(this.transactionService
                .convert(1L, TransactionWrapper.builder().build()))
                .thenReturn(Transaction
                        .builder()
                        .userId(1L)
                        .build());

        given()
                .body(getJson())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/converter/{userId}", 1L)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    void deveRertornarBadRequestAoNaoInformarValor_QuandoEnviarUmaTransacao(){
        when(this.transactionService
                .convert(1L, TransactionWrapper.builder().build()))
                .thenThrow(CurrencyNotFoundException.class);

        given()
                .body(getJson())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/converter/{userId}", 1L)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private static String getJson(){
        TransactionRequestWrapper transactionRequestWrapper = TransactionRequestWrapper.builder()
                .amount(null)
                .targetCurrency("BRL")
                .originCurrency("USD")
                .build();
        return new Gson().toJson(transactionRequestWrapper);
    }
}
