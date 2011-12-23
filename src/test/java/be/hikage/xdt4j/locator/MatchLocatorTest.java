package be.hikage.xdt4j.locator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.intellij.lang.annotations.Language;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MatchLocatorTest {


    @Test
    public void testMatchLocatorNoParameter() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings/add");


        Locator matchLocator = new MatchLocator(null);

        String resultXPath = matchLocator.generateXPath(addElement);
        assertNotNull(resultXPath);
        assertEquals("/configuration/appSettings/add", resultXPath);
    }


    @Test
    public void testMatchLocatorSingleParameter() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings/add");


        Locator matchLocator = new MatchLocator("value");

        String resultXPath = matchLocator.generateXPath(addElement);
        assertNotNull(resultXPath);
        assertEquals("/configuration/appSettings/add[@value=\"value2-live\"]", resultXPath);
    }

    @Test
    public void testMatchLocatorMultipleParameter() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings/add");


        Locator matchLocator = new MatchLocator("value,key");

        String resultXPath = matchLocator.generateXPath(addElement);
        assertNotNull(resultXPath);
        assertEquals("/configuration/appSettings/add[@value=\"value2-live\" and @key=\"key2\"]", resultXPath);
    }

    private Element loadElement(String xmlDoc, String xPath) {
        try {
            return (Element) DocumentHelper.parseText(xmlDoc).selectSingleNode(xPath);
        } catch (Exception e) {
            throw new RuntimeException("Bad XPath expression", e);
        }

    }
}
