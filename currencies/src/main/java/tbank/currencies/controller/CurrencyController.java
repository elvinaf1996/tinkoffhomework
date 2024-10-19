package tbank.currencies.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tbank.currencies.model.CurrencyConversionRequest;
import tbank.currencies.model.CurrencyConversionResponse;
import tbank.currencies.model.CurrencyRate;
import tbank.currencies.services.CurrencyService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
@Api(value = "Currency API", tags = {"Currency"})
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(value = "/rates/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get currency by code",
            notes = "Get currency by code.")
    //TODO могут быть разные сообщения, как прописать варианты
    //TODO при 200 ответ как прописать
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful get currency"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 404, message = "Currency not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public CurrencyRate getCurrencyByCode(@PathVariable String code) {
        return currencyService.getCurrencyRate(code);
    }

    @PostMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Convert currency",
            notes = "Converts amount from one currency to another using the latest exchange rates.")
    //TODO могут быть разные сообщения, как прописать варианты
    //TODO при 200 ответ как прописать
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful currency conversion"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 404, message = "Currency not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public CurrencyConversionResponse convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) {
        return currencyService.convertCurrency(request);
    }
}
