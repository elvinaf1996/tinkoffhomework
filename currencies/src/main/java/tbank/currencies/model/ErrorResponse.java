package tbank.currencies.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Schema(description = "Error response model")
public class ErrorResponse {

    @Schema(description = "Error code representing the type of error", examples = {"400", "404"})
    private int code;
    @Schema(description = "Detailed error message", examples = {"Currency with code 'code' not found",
            "Currency with code 'code' not exist", "fromCurrency must not be null", "toCurrency must not be null",
            "amount must not be null", "amount must not be not negative"})
    private String errorMessage;
}
