package be.hikage.xdt4j.locator;

import org.dom4j.Element;
import org.intellij.lang.annotations.Language;
import org.junit.Test;

import static be.hikage.xdt4j.util.TestUtils.loadElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocatorUtilsTest {

    @Test
     public void testUniqueXPathFromLocator_nolocatorinfo() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings id=\"1\">\n        <add key=\"key1\" value=\"value1\"/>\n    </appSettings>\n    <appSettings id=\"2\">\n        <add key=\"key2\" value=\"value2\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings[@id='2']/add");


        String expectedXPath = LocatorUtils.uniqueXPathFromLocator(addElement);

        assertNotNull(expectedXPath);
        assertEquals("/configuration/appSettings/add", expectedXPath);
    }

    @Test
    public void testUniqueXPathFromLocator_matchlocator() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings id=\"1\">\n        <add key=\"key1\" value=\"value1\"/>\n    </appSettings>\n    <appSettings id=\"2\" xdt:Locator=\"Match(id)\">\n        <add key=\"key2\" value=\"value2\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings[@id=\"2\"]/add");


        String expectedXPath = LocatorUtils.uniqueXPathFromLocator(addElement);

        assertNotNull(expectedXPath);
        assertEquals("/configuration/appSettings[@id=\"2\"]/add", expectedXPath);
    }

    @Test
    public void testUniqueXPathFromLocator_conditionlocator() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings id=\"1\">\n        <add key=\"key1\" value=\"value1\"/>\n    </appSettings>\n    <appSettings id=\"2\" xdt:Locator=\"Condition(@id='2')\">\n        <add key=\"key2\" value=\"value2\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings[@id=\"2\"]/add");


        String expectedXPath = LocatorUtils.uniqueXPathFromLocator(addElement);

        assertNotNull(expectedXPath);
        assertEquals("/configuration/appSettings[@id='2']/add", expectedXPath);
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testUniqueXPathFromLocator_conditionxpath() {

        @Language("XML")
        final String xmlDoc = "<configuration xmlns:xdt=\"http://schemas.microsoft.com/XML-Document-Transform\">\n    <appSettings id=\"1\">\n        <add key=\"key1\" value=\"value1\"/>\n    </appSettings>\n    <appSettings id=\"2\" xdt:Locator=\"XPath(/)\">\n        <add key=\"key2\" value=\"value2\"/>\n    </appSettings>\n</configuration>";

        Element addElement = loadElement(xmlDoc, "/configuration/appSettings[@id=\"2\"]/add");


        String expectedXPath = LocatorUtils.uniqueXPathFromLocator(addElement);

        assertNotNull(expectedXPath);
        assertEquals("/configuration/appSettings[@id='2']/add", expectedXPath);
    }

}
