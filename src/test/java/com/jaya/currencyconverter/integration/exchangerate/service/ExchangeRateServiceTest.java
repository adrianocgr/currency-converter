package com.jaya.currencyconverter.integration.exchangerate.service;

import com.jaya.currencyconverter.integration.exchangerate.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @MockBean
    private ExchangeRateServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    private static final String url = "http://localhost:8080";


    @Test
    public void whenFetchingExchangeRate_returnSucess(){
        ExchangeRate mockExchangeRate = ExchangeRate.builder()
                .sucess(Boolean.TRUE)
                .timestamp(1)
                .base("EUR")
                .date(LocalDate.now())
                .rates(new HashMap<>())
                .build();

        Mockito.when(restTemplate.getForEntity(url, ExchangeRate.class))
                .thenReturn(new ResponseEntity<>(mockExchangeRate, HttpStatus.OK));
        ExchangeRate exchangeRate = service.get();
        assertThat(mockExchangeRate, is(equalTo(exchangeRate)));
    }

}
