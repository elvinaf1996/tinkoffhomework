package tbank.currencies;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.currencies.controller.CurrencyController;
import tbank.currencies.model.CurrencyConversionRequest;

import java.time.Duration;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tbank.currencies.utils.DoubleUtils.roundToTwoDecimalPlaces;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConvertTest extends BaseCurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyController currencyController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String API_URL = "/currencies/convert";

    @Test
    public void assertSecondRequestGetFromCash() throws Exception {
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = 123.52;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value(fromCurrency))
                .andExpect(jsonPath("$.toCurrency").value(toCurrency))
                .andExpect(jsonPath("$.convertedAmount").value(getConvertedAmount(currencyConversionRequest)));

        assertTimeout(Duration.ofSeconds(15), () -> {
            mockMvc.perform(post(API_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                    .andExpect(status().isOk());
        });
    }

    @Test
    public void assertGetValueCorrect() throws Exception {
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = 78.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value(fromCurrency))
                .andExpect(jsonPath("$.toCurrency").value(toCurrency))
                .andExpect(jsonPath("$.convertedAmount").value(getConvertedAmount(currencyConversionRequest)));
    }

    @Test
    public void assertCurrencyNotFoundIfThisFromCurrencyNotInRequestFromCb() throws Exception {
        String fromCurrency = getExistingCodeButNotExistInRequest();
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = 78.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);
        String errorMessage = format("Currency with code '%s' not found", fromCurrency);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void assertCurrencyNotFoundIfToCurrencyNotInRequestFromCb() throws Exception {
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = getExistingCodeButNotExistInRequest();
        double amount = 178.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);
        String errorMessage = format("Currency with code '%s' not found", toCurrency);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void assertFromCurrencyNotExistError() throws Exception {
        String fromCurrency = generateNotExistValute();
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = 78.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);
        String errorMessage = format("Currency with code '%s' not exist", fromCurrency);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void assertToCurrencyNotExistError() throws Exception {
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = generateNotExistValute();
        double amount = 278.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);
        String errorMessage = format("Currency with code '%s' not exist", toCurrency);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void failureFromCurrencyIsNull() throws Exception {
        String errorMessage = "fromCurrency must not be null";
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = 10278.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(null, toCurrency, amount);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void failureToCurrencyIsNull() throws Exception {
        String errorMessage = "toCurrency must not be null";
        String fromCurrency = getRandomValuteFromCBRequest();
        double amount = 10278.53;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, null, amount);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void failureAmountIsNull() throws Exception {
        String errorMessage = "amount must not be null";
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = getRandomValuteFromCBRequest();
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, null);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void failureAmountIsLessThanNull() throws Exception {
        String errorMessage = "amount must not be not negative";
        String fromCurrency = getRandomValuteFromCBRequest();
        String toCurrency = getRandomValuteFromCBRequest();
        double amount = -1000;
        CurrencyConversionRequest currencyConversionRequest = new CurrencyConversionRequest(fromCurrency, toCurrency, amount);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyConversionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    private double getConvertedAmount(CurrencyConversionRequest request) {
        String toCurrency = request.getToCurrency();
        String fromCurrency = request.getFromCurrency();
        double toCurrencyRate;
        double fromCurrencyRate;

        if (toCurrency.equals("RUB")) {
            toCurrencyRate = 1;
        } else {
            toCurrencyRate = valutesWithValue.get(toCurrency);
        }

        if (fromCurrency.equals("RUB")) {
            fromCurrencyRate = 1;
        } else {
            fromCurrencyRate = valutesWithValue.get(fromCurrency);
        }

        double rate = fromCurrencyRate / toCurrencyRate;

        return roundToTwoDecimalPlaces(request.getAmount() * rate);
    }
}