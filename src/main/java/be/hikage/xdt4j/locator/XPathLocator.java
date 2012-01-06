package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtException;
import org.dom4j.Element;

public class XPathLocator extends Locator {
    public XPathLocator(String parameter) {
        super(parameter);
        if (parameter == null || parameter.isEmpty())
            throw new XdtException("Parameter is mandatory for XPathLocator");
    }

    @Override
    public String generateXPath(Element target) {
        return getParameter();
    }

    @Override
    public String generateCondition(Element target) {
        throw new UnsupportedOperationException("XPathLocator do not allow condition to relative element");
    }
}
