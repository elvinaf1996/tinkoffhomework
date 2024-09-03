package tbank.homework2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void saveFile(String fileContent, String filePath) throws IOException {
        Path path = Path.of(filePath);

        try {
            if (Files.exists(path)) {
                logger.warn("Файл уже существует: {}", path.toAbsolutePath());
            } else {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                logger.info("Файл создан: {}", path.toAbsolutePath());
            }

            Files.writeString(path, fileContent);
            logger.info("Файл успешно записан: {}", path.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Ошибка при записи в файл: {}", e.getMessage());
            throw new IOException("Ошибка при записи в файл", e);
        }
    }
}
