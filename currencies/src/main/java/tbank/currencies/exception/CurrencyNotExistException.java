package tbank.currencies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyNotExistException extends RuntimeException {
    public CurrencyNotExistException(String code) {
        super(format("Currency with code '%s' not exist", code));
    }
}