package tbank.currencies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionResponse {

    private String fromCurrency;
    private String toCurrency;
    private Double convertedAmount;
}
