package tbank.currencies.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tbank.currencies.exception.CurrencyNotFoundException;
import tbank.currencies.model.*;
import tbank.currencies.utils.EDatePattern;

import static tbank.currencies.utils.DoubleUtils.getDoubleFromStringWithComma;
import static tbank.currencies.utils.DoubleUtils.roundToTwoDecimalPlaces;
import static tbank.currencies.utils.StringUtils.getDateTodayAsPattern;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    @Value("${currency.api-url}")
    private String apiUrl;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "currencyRates")
    @Cacheable(value = "currencyRates", key = "#code", unless = "#result == null")
    public CurrencyRate getCurrencyRate(String code) {
        Valute valute = getValuteByCode(code);
        double valuteCurrency = getDoubleFromStringWithComma(valute.getVunitRate());

        return new CurrencyRate(valute.getCharCode(),
                roundToTwoDecimalPlaces(valuteCurrency));
    }

    private ValCurs getValCurs() {
        String fullUrl = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("date_req", getDateTodayAsPattern(EDatePattern.PATTERN_1))
                .toUriString();

        return restTemplate.getForObject(fullUrl, ValCurs.class);
    }

    private Valute getValuteByCode(String code) {
        return getValCurs().getValuteList()
                .stream()
                .filter(v -> v.getCharCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    @CircuitBreaker(name = "convertCurrency")
    @Cacheable(value = "convertCurrency", key = "#request.fromCurrency + '_' + #request.toCurrency + '_' + #request.amount", unless = "#result == null")
    public CurrencyConversionResponse convertCurrency(CurrencyConversionRequest request) {
        String toCurrency = request.getToCurrency();
        String fromCurrency = request.getFromCurrency();
        double toCurrencyRate;
        double fromCurrencyRate;

        if (toCurrency.equals("RUB")) {
            toCurrencyRate = 1;
        } else {
            Valute toCurrencyValute = getValuteByCode(request.getToCurrency());
            toCurrencyRate = getDoubleFromStringWithComma(toCurrencyValute.getVunitRate());
        }

        if (fromCurrency.equals("RUB")) {
            fromCurrencyRate = 1;
        } else {
            Valute fromCurrencyValute = getValuteByCode(request.getFromCurrency());
            fromCurrencyRate = getDoubleFromStringWithComma(fromCurrencyValute.getVunitRate());
        }

        double rate = fromCurrencyRate / toCurrencyRate;
        double convertedAmount = request.getAmount() * rate;

        CurrencyConversionResponse response = new CurrencyConversionResponse();
        response.setFromCurrency(request.getFromCurrency());
        response.setToCurrency(request.getToCurrency());
        response.setConvertedAmount(roundToTwoDecimalPlaces(convertedAmount));
        return response;
    }
}
