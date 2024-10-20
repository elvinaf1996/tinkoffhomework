package tbank.currencies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurrencyRate {

    private String currency;
    private Double rate;
}
