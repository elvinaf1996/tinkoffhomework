package tbank.currencies.model;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyConversionRequest {

    @NotNull(message = "fromCurrency must not be null")
    private String fromCurrency;

    @NotNull(message = "toCurrency must not be null")
    private String toCurrency;

    @NotNull(message = "amount must not be null")
    @PositiveOrZero(message = "amount must not be not negative")
    private Double amount;
}
