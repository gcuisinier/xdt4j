package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtException;
import org.dom4j.Element;
import org.intellij.lang.annotations.Language;
import org.junit.Test;

import static be.hikage.xdt4j.util.TestUtils.loadElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConditionLocatorTest {


    @Test(expected = XdtException.class)
    public void testMatchLocatorNoParameter() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings/add");


        Locator matchLocator = new ConditionLocator(null);


    }


    @Test
    public void testMatchLocatorSingleParameter() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings>\n        <add key=\"key2\" value=\"value2-live\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings/add");


        Locator matchLocator = new ConditionLocator("@value=\"value2-live\"");

        String resultXPath = matchLocator.generateXPath(addElement);
        assertNotNull(resultXPath);
        assertEquals("/configuration/appSettings/add[@value=\"value2-live\"]", resultXPath);
    }




}
