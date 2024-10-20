package tbank.currencies.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tbank.currencies.model.CurrencyConversionRequest;
import tbank.currencies.model.CurrencyConversionResponse;
import tbank.currencies.model.CurrencyRate;
import tbank.currencies.model.ErrorResponse;
import tbank.currencies.services.CurrencyService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
@Tag(name = "Currency API", description = "Currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(value = "/rates/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get currency by code", description = "Get currency by code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful get currency"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Currency not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CurrencyRate getCurrencyByCode(@PathVariable String code) {
        return currencyService.getCurrencyRate(code);
    }

    @PostMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get currency by code", description = "Get currency by code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful get currency"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Currency not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CurrencyConversionResponse convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) {
        return currencyService.convertCurrency(request);
    }
}
