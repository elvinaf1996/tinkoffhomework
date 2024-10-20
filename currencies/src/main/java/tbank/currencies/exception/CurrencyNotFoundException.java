package tbank.currencies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tbank.currencies.utils.EValute;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String code) {
        super(format("Currency with code '%s' not found", code));
        boolean isCurrencyExist = EValute.isValuteExist(code);
        if (!isCurrencyExist) {
            throw new CurrencyNotExistException(code);
        }
    }
}