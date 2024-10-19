package tbank.currencies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Error {

    private int code;
    private String errorMessage;
}
