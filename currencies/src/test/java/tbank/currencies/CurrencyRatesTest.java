package tbank.currencies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.currencies.controller.CurrencyController;

import java.time.Duration;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyRatesTest extends BaseCurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyController currencyController;

    private final String API_URL = "/currencies/rates/{code}";

    @Test
    public void assertSecondRequestGetFromCash() throws Exception {
        String existingCode = getRandomValuteFromCBRequest();
        mockMvc.perform(get(API_URL, existingCode))
                .andExpect(status().isOk());

        assertTimeout(Duration.ofSeconds(15), () -> {
            mockMvc.perform(get(API_URL, existingCode))
                    .andExpect(status().isOk());
        });
    }

    @Test
    public void assertGetValueCorrect() throws Exception {
        String existingCode = getRandomValuteFromCBRequest();

        mockMvc.perform(get(API_URL, existingCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currency").value(existingCode))
                .andExpect(jsonPath("$.rate").value(valutesWithValue.get(existingCode)));
    }

    @Test
    public void assertCurrencyNotFoundIfThisCurrencyNotInRequestFromCb() throws Exception {
        String existingCodeButNotExistInRequest = getExistingCodeButNotExistInRequest();
        String errorMessage = format("Currency with code '%s' not found", existingCodeButNotExistInRequest);

        mockMvc.perform(get(API_URL, existingCodeButNotExistInRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }

    @Test
    public void assertCurrencyNotExistError() throws Exception {
        String notExistingCode = generateNotExistValute();
        String errorMessage = format("Currency with code '%s' not exist", notExistingCode);

        mockMvc.perform(get(API_URL, notExistingCode))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));
    }
}