package homework2;

import org.junit.Test;
import tbank.homework2.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static utils.AssertionsUtils.assertEquals;
import static utils.AssertionsUtils.assertThatThrownByWithErrorWith;

public class FileUtilsTest {

    public static final String OUTPUT_DIRECTORY = "build/downloaded_files/";
    public static final String TEST_FILE_CONTENT = "Тестовое содержимое файла";
    public static final String TEST_FILE_CONTENT_XML = "<City><slug>Sample City</slug><coords><lat>123.23</lat><lon>1323.12</lon></coords></City>";
    public static final String TEST_XML_FILE_NAME = "test.xml";
    private static final String TEST_FILE_NAME = "testFile.txt";
    private static final String TEST_JSON_FILE_NAME = "testFile.json";
    private Path pathTxt = Path.of(OUTPUT_DIRECTORY.concat(TEST_FILE_NAME));
    private Path pathXml = Path.of(OUTPUT_DIRECTORY.concat(TEST_XML_FILE_NAME));
    private Path pathJson = Path.of(OUTPUT_DIRECTORY.concat(TEST_JSON_FILE_NAME));

    @Test
    public void testSaveFileCreatesNewFile() throws IOException {
        deleteIfFileExist(pathTxt);
        FileUtils.saveFile(TEST_FILE_CONTENT, OUTPUT_DIRECTORY.concat(TEST_FILE_NAME));
        assertTrue(Files.exists(pathTxt));
        String error = format("Ожидал что после сохранения файла в директории '%s' появится файл с именем '%s', по факту не нашел файл",
                OUTPUT_DIRECTORY, TEST_FILE_NAME);

        assertEquals(TEST_FILE_CONTENT, Files.readString(pathTxt), error);
        Files.delete(pathTxt);
    }

    @Test
    public void testSaveFileFileAlreadyExists() throws IOException {
        deleteIfFileExist(pathXml);
        Files.createDirectories(pathXml.getParent());
        Files.writeString(pathXml, TEST_FILE_CONTENT_XML);
        FileUtils.saveFile(TEST_FILE_CONTENT_XML, OUTPUT_DIRECTORY.concat(TEST_XML_FILE_NAME));
        FileUtils.saveFile(TEST_FILE_CONTENT_XML, OUTPUT_DIRECTORY.concat(TEST_XML_FILE_NAME));
        String error = format("Ожидал что после сохранения файла в директории '%s' появится файл с именем '%s', по факту не нашел файл",
                OUTPUT_DIRECTORY, TEST_FILE_NAME);
        assertEquals(TEST_FILE_CONTENT_XML, Files.readString(pathXml), error);
        Files.delete(pathXml);
    }

    @Test
    public void testProcessFileSuccess() throws Exception {
        String cityJson = "{\"key\": \"value\"}";
        FileUtils.saveFile(cityJson, OUTPUT_DIRECTORY.concat(TEST_JSON_FILE_NAME));
        Files.delete(pathJson);
    }

    @Test
    public void testProcessFileFailure() {
        String cityJson = null;
        assertThatThrownByWithErrorWith(() -> FileUtils.saveFile(cityJson, OUTPUT_DIRECTORY.concat(TEST_JSON_FILE_NAME)),
                NullPointerException.class, "Ошибка при записи в файл, передан null для записи");
    }

    private void deleteIfFileExist(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createDirectories(path.getParent());
    }
}
