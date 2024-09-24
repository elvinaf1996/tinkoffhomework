package homework2;

import org.junit.Test;
import tbank.homework2.models.City;
import tbank.homework2.models.Coordinates;
import tbank.homework2.processor.JsonProcessor;

import java.io.IOException;

import static java.lang.String.format;
import static utils.AssertionsUtils.assertEquals;
import static utils.AssertionsUtils.assertThatThrownByWithErrorWith;

public class JsonProcessorTest {

    public static final String INPUT_DIRECTORY = "src/main/resources/";
    public static final String INPUT_JSON_NAME = "cityTest.json";
    public static final String INPUT_ERROR_JSON_NAME = "city-error.json";

    @Test
    public void testGetObjectFromJsonSuccess() throws IOException {
        Coordinates coordinates = new Coordinates(123.23, 1323.12);
        City expectedCity = new City("Sample City", coordinates);
        City actualCity = JsonProcessor.getObjectFromJson(INPUT_DIRECTORY.concat(INPUT_JSON_NAME), City.class);
        String error = format("Ожидал объект '%s', по факту получил '%s'", expectedCity, actualCity);
        assertEquals(actualCity, expectedCity, error);
    }

    @Test
    public void testGetObjectFromJsonFileNotFound() {
        String filePath = INPUT_DIRECTORY.concat("non_existent.json");
        assertThatThrownByWithErrorWith(() -> JsonProcessor.getObjectFromJson(filePath, City.class),
                IOException.class, "Не найден файл по пути ".concat(filePath));
    }

    @Test
    public void testGetObjectFromJsonParsingError() {
        String filePathWithError = INPUT_DIRECTORY + INPUT_ERROR_JSON_NAME;
        assertThatThrownByWithErrorWith(() -> JsonProcessor.getObjectFromJson(filePathWithError, City.class),
                RuntimeException.class, format("Невозможно преобразовать файл по пути '%s' в класс '%s'", filePathWithError, City.class.getName()));
    }
}
