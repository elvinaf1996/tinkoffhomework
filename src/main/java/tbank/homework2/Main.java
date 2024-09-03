package tbank.homework2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import tbank.homework2.model.City;
import tbank.homework2.model.JsonModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        logger.debug("DEBUG: Работа с файлом city.json");
        String cityFilename = "src/main/resources/city.json";
        City city = getObjectFromJson(cityFilename, City.class);
        String cityXml = toXml(city);
        String pathToSaveCityXml = "build/".concat("cityXml.xml");
        saveFile(cityXml, pathToSaveCityXml);

        logger.debug("DEBUG: Работа с файлом city-error.json");
        String cityErrorFilename = "src/main/resources/city-error.json";
        City cityError = getObjectFromJson(cityErrorFilename, City.class);
        String cityErrorXml = toXml(cityError);
        String pathToSaveCityErrorXml = "build/".concat("cityXml.xml");
        saveFile(cityErrorXml, pathToSaveCityErrorXml);
    }

    public static <T> T getObjectFromJson(String filePath, Class<T> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorText = format("Ошибка преобразования данных из файла '%s' в класс '%s'", filePath, classType.toString());
        File file = new File(filePath);

        if (!file.exists()) {
            logger.error("ERROR: Не найден файл по пути {}", filePath);
            throw new RuntimeException("Не найден файл по пути " + filePath);
        }

        logger.warn("WARN: Возможна ошибка преобразования файла в класс");

        try {
            logger.info("INFO: Преобразование файла '{}' в объект '{}'", filePath, classType);
            return objectMapper.readValue(file, classType);
        } catch (IOException e) {
            logger.error("ERROR: ".concat(errorText));
            throw new RuntimeException(e);
        }
    }

    public static <T> String toXml(JsonModel model) {
        logger.warn("WARN: Возможна ошибка преобразования объекта в xml");

        try {
            logger.info("INFO: Преобразую объект '{}' в xml", model);
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        } catch (Exception e) {
            String className = model.getClass().getName();
            logger.error("ERROR: Не смог преобразовать класс '{}' в xml", className);
            throw new RuntimeException(e);
        }
    }

    public static void saveFile(String fileContent, String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (Files.exists(path)) {
            logger.warn("WARN: Файл уже существует: {}", path.toAbsolutePath());
        } else {
            Files.createFile(path);
            logger.info("INFO: Файл создан: {}", path.toAbsolutePath());
        }

        logger.info("INFO: Файл создан: {}", path.toAbsolutePath());
        logger.warn("WARN: Возможна ошибка сохранения данных в файл");

        try {
            Files.write(path, fileContent.getBytes());
            logger.info("INFO: Файл успешно создан: {}", path.toAbsolutePath());
        } catch (IOException e) {
            logger.error("ERROR: Ошибка при записи в файл: {}", e.getMessage());
            throw new IOException(e);
        }
    }
}