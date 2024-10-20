package tbank.currencies.utils;

import static java.lang.Double.parseDouble;

public class DoubleUtils {

    public static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static double getDoubleFromStringWithComma(String value) {
        return parseDouble(value.replace(",", "."));
    }
}
