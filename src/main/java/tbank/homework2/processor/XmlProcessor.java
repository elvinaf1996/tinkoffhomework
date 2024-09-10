package tbank.homework2.processor;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

public class XmlProcessor {

    private static final Logger logger = LoggerFactory.getLogger(XmlProcessor.class);
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static String toXml(Object model) {
        logger.trace("Преобразую объект '{}' в xml", model);

        if (isNull(model)) {
            return "";
        }

        try {
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        } catch (Exception e) {
            logger.error("Не смог преобразовать класс '{}' в xml", model.getClass().getName());
            throw new RuntimeException("Ошибка преобразования в XML", e);
        }
    }
}
