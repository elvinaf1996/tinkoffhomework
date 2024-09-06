package tbank.homework2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tbank.homework2.models.City;
import tbank.homework2.processor.JsonProcessor;
import tbank.homework2.processor.XmlProcessor;
import tbank.homework2.utils.FileUtils;

import java.io.IOException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final String INPUT_DIRECTORY = "src/main/resources/";
    public static final String OUTPUT_DIRECTORY = "build/";
    public static final String INPUT_JSON_NAME = "city.json";
    public static final String INPUT_ERROR_JSON_NAME = "city-error.json";
    public static final String OUTPUT_FILENAME = "cityXml.xml";

    public static void main(String[] args) {
        try {
            logger.info("Запуск обработки файлов...");
            processFile(INPUT_JSON_NAME);
            processFile(INPUT_ERROR_JSON_NAME);
            logger.info("Обработка файлов завершена.");
        } catch (Exception e) {
            logger.error("Произошла ошибка во время обработки файлов: {}", e.getMessage(), e);
        }
    }

    private static void processFile(String inputJsonName) {
        logger.debug("Работа с файлом {}", inputJsonName);
        String cityFilename = INPUT_DIRECTORY + inputJsonName;

        try {
            City city = JsonProcessor.getObjectFromJson(cityFilename, City.class);
            String cityXml = XmlProcessor.toXml(city);
            String pathToSaveCityXml = OUTPUT_DIRECTORY + OUTPUT_FILENAME;
            FileUtils.saveFile(cityXml, pathToSaveCityXml);
            logger.info("Файл успешно сохранен: {}", pathToSaveCityXml);
        } catch (IOException e) {
            logger.error("Ошибка при обработке файла '{}': {}", inputJsonName, e.getMessage());
        }
    }
}