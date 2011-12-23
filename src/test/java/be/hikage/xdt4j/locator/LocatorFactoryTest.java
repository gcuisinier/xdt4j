package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtConstants;
import be.hikage.xdt4j.XdtException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocatorFactoryTest {


    @Test
    public void testMatchLocator() {

        Element element = createDocument("Match");

        Object result = LocatorFactory.createLocator(element);
        assertNotNull(result);
        assertTrue("Result is not a MatchLocator", result instanceof MatchLocator);
        Locator matchLocator = (Locator) result;
        assertNull("Argument must be null", matchLocator.getParameter());

    }


    @Test
    public void testMatchLocatorParam() {

        Element element = createDocument("Match(key)");

        Object result = LocatorFactory.createLocator(element);
        assertNotNull(result);
        assertTrue("Result is not a MatchLocator", result instanceof MatchLocator);
        Locator matchLocator = (Locator) result;
        assertEquals("key", matchLocator.getParameter());

    }

    @Test
    public void testMatchLocatorParams() {

        Element element = createDocument("Match(key,key2)");

        Object result = LocatorFactory.createLocator(element);
        assertNotNull(result);
        assertTrue("Result is not a MatchLocator", result instanceof MatchLocator);
        Locator matchLocator = (Locator) result;
        assertEquals("key,key2", matchLocator.getParameter());

    }


    @Test(expected = XdtException.class)
    public void testConditionLocator() {

        Element element = createDocument("Condition");

        Object result = LocatorFactory.createLocator(element);

    }


    @Test
    public void testConditionLocatorParam() {

        Element element = createDocument("Condition(key)");

        Object result = LocatorFactory.createLocator(element);
        assertNotNull(result);
        assertTrue("Result is not a ConditionLocator", result instanceof ConditionLocator);
        Locator matchLocator = (Locator) result;
        assertEquals("key", matchLocator.getParameter());

    }

    @Test(expected = XdtException.class)
    public void testXPathLocator() {

        Element element = createDocument("XPath");

        Object result = LocatorFactory.createLocator(element);

    }


    @Test
    public void testXPathLocatorParam() {

        Element element = createDocument("XPath(key)");

        Object result = LocatorFactory.createLocator(element);
        assertNotNull(result);
        assertTrue("Result is not a XPath", result instanceof XPathLocator);
        Locator matchLocator = (Locator) result;
        assertEquals("key", matchLocator.getParameter());

    }


    @Test(expected = XdtException.class)
    public void testInvalidLocator() {

        Element element = createDocument("#######");

        Object result = LocatorFactory.createLocator(element);
        assertNull("Unknown validator must be return null", result);

    }

    @Test
    public void testUnknownLocator() {

        Element element = createDocument("Unknown(key)");

        Object result = LocatorFactory.createLocator(element);
        assertNull("Unknown validator must be return null", result);

    }


    private Element createDocument(String locatorType) {

        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("root");

        document.setRootElement(root);

        Element child = DocumentHelper.createElement("child");

        child.addAttribute(QName.get("Locator", XdtConstants.XDT_NAMESPACE), locatorType);

        return child;


    }
}
