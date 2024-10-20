package tbank.currencies.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class StringUtils {

    public static String getDateTodayAsPattern(EDatePattern datePattern) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern.getFormat());

        return today.format(formatter);
    }

    public static String generateLatinString(int size) {
        String latinWorld = "qwertyuiopasdfghjklzxcvbnm";
        char[] worlds = latinWorld.toCharArray();
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(worlds.length);
            stringBuilder.append(worlds[randomIndex]);
        }

        return stringBuilder.toString();
    }
}
