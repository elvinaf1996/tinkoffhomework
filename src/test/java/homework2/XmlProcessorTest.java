package homework2;

import org.junit.Test;
import tbank.homework2.models.City;
import tbank.homework2.models.Coordinates;
import tbank.homework2.processor.XmlProcessor;

import static java.lang.String.format;
import static utils.AssertionsUtils.asserStringIsEmpty;
import static utils.AssertionsUtils.assertEquals;

public class XmlProcessorTest {

    @Test
    public void testToXmlSuccess() {
        Coordinates coordinates = new Coordinates(123.23, 1323.12);
        City city = new City("Sample City", coordinates);
        String expectedXml = "<City><slug>Sample City</slug><coords><lat>123.23</lat><lon>1323.12</lon></coords></City>";

        String actualXml = XmlProcessor.toXml(city).replace("\r\n", "")
                .replace("  ", "");
        String error = format("Ожидал xml: '%s', получил: '%s'", expectedXml, actualXml);
        assertEquals(actualXml, expectedXml, error);
    }

    @Test
    public void testToXmlConversionWithNullObject() {
        String xml = XmlProcessor.toXml(null);
        String error = "Ожидал, что вернется пустая строка, по факту получил: ".concat(xml);
        asserStringIsEmpty(xml, error);
    }
}
