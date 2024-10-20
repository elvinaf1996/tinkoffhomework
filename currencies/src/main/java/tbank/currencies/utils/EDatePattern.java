package tbank.currencies.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EDatePattern {

    PATTERN_1("dd/MM/yyyy");

    private String format;
}
