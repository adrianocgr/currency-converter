package com.jaya.currencyconverter.integration.exchangerate.service;

import com.jaya.currencyconverter.CurrencyConverterApplication;
import com.jaya.currencyconverter.integration.exchangerate.exception.ExchangerateApiKeyNotInformedException;
import com.jaya.currencyconverter.integration.exchangerate.exception.ExchangerateBaseNotInformedException;
import com.jaya.currencyconverter.integration.exchangerate.exception.ExchangerateRequestException;
import com.jaya.currencyconverter.integration.exchangerate.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final String url = "http://api.exchangeratesapi.io/latest?base={base}&access_key={key}";

    private static Logger logger = LoggerFactory.getLogger(CurrencyConverterApplication.class);

    @Value("${EXCHANGERATES_BASE}")
    private String base;

    @Value("${EXCHANGERATES_KEY}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ExchangeRate get() {
        validateEnvironmentVariables();
        ExchangeRate exchangeRate = getRates();
        return exchangeRate;
    }

    private void validateEnvironmentVariables() {
        if (base == null || base.isEmpty())
            throw new ExchangerateBaseNotInformedException();
        if (apiKey == null || apiKey.isEmpty())
            throw new ExchangerateApiKeyNotInformedException();
    }

    private void validateRequestResult(ExchangeRate exchangeRate) {
        if (exchangeRate.getError() != null) {
            String message = exchangeRate.getError().getInfo();
            String code = exchangeRate.getError().getCode().toString();
            StringBuilder formattedMessage = new StringBuilder();
            formattedMessage.append("Error code ");
            formattedMessage.append(code);
            formattedMessage.append(" - ");
            formattedMessage.append(message);
            throw new ExchangerateRequestException(formattedMessage.toString());
        }
    }

    private ExchangeRate getRates() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("base", base);
            uriVariables.put("key", apiKey);

            HttpEntity<ExchangeRate> entity = new HttpEntity<ExchangeRate>(headers);
            ExchangeRate exchangeRate = restTemplate.exchange(url, HttpMethod.GET, entity, ExchangeRate.class, uriVariables).getBody();
            validateRequestResult(exchangeRate);
            return exchangeRate;
        } catch (Exception ex) {
            logger.error("An error occurred while integrating with the ExchangeRate API, reason: " + ex.getMessage());
            throw new ExchangerateRequestException("An error occurred while integrating with the ExchangeRate API");
        }
    }
}
