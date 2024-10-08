package tbank.homework2.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

public class JsonProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JsonProcessor.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getObjectFromJson(String filePath, Class<T> classType) throws IOException {
        logger.trace("Получаю объект класса '{}' из json по пути {}", classType.getName(), filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            logger.error("Не найден файл по пути {}", filePath);
            throw new IOException("Не найден файл по пути " + filePath);
        }

        logger.info("Преобразование файла '{}' в объект '{}'", filePath, classType);

        try {
            return objectMapper.readValue(file, classType);
        } catch (Exception ex) {
            throw new RuntimeException(format("Невозможно преобразовать файл по пути '%s' в класс '%s'", filePath, classType.getName()));
        }
    }
}
