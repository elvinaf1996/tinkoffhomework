package tbank.currencies;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tbank.currencies.model.ValCurs;
import tbank.currencies.model.Valute;
import tbank.currencies.utils.EDatePattern;
import tbank.currencies.utils.EValute;

import java.util.*;

import static java.lang.Double.parseDouble;
import static tbank.currencies.utils.DoubleUtils.roundToTwoDecimalPlaces;
import static tbank.currencies.utils.StringUtils.generateLatinString;
import static tbank.currencies.utils.StringUtils.getDateTodayAsPattern;

@SpringBootTest
public abstract class BaseCurrencyTest {

    @Value("${currency.api-url}")
    private String apiUrl;

    protected List<String> valuteValuesFromCb = new ArrayList<>();
    protected Map<String, Double> valutesWithValue = new HashMap<>();

    @PostConstruct
    public void init() {
        saveAllValutesFromCB();
    }

    protected void saveAllValutesFromCB() {
        String fullUrl = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("date_req", getDateTodayAsPattern(EDatePattern.PATTERN_1))
                .toUriString();

        ValCurs valCurs = new RestTemplate().getForObject(fullUrl, ValCurs.class);
        List<Valute> valuteList = valCurs.getValuteList();
        saveAllValuteCode(valuteList);
        saveValuteAndValues(valuteList);
    }

    protected void saveAllValuteCode(List<Valute> valuteList) {
        valuteList.forEach(v -> valuteValuesFromCb.add(v.getCharCode()));
    }

    protected void saveValuteAndValues(List<Valute> valuteList) {
        for (Valute valute : valuteList) {
            double valuteValue = parseDouble(valute.getVunitRate().replace(",", "."));
            valutesWithValue.put(valute.getCharCode(), roundToTwoDecimalPlaces(valuteValue));
        }
    }

    protected String getRandomValuteFromCBRequest() {
        return valuteValuesFromCb.get(new Random().nextInt(valuteValuesFromCb.size()));
    }

    protected String getExistingCodeButNotExistInRequest() {
        for (int i = 0; i < 100; i++) {
            String valute = EValute.getRandomValute().toString();
            if (!valutesWithValue.containsKey(valute)) {
                return valute;
            }
        }

        throw new RuntimeException("Не смог сгенерировать существующую валюту, которой нет в ответе от ЦБ");
    }

    protected String generateNotExistValute() {
        String randomNotExistingValute;
        for (int i = 0; i < 5; i++) {
            randomNotExistingValute = generateLatinString(3).toUpperCase();
            if (!EValute.isValuteExist(randomNotExistingValute)) {
                return randomNotExistingValute;
            }
        }

        throw new RuntimeException("Не смог сгенерировать несуществующую валюту с 5-ти попыток");
    }
}
