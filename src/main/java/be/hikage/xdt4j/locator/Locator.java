package be.hikage.xdt4j.locator;

import org.dom4j.Element;

public abstract class Locator {

    protected String parameter;

    public Locator(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    /**
     * Generate a XPath for the privided Element with the correct selection query
     * accoding the Locator condition
     *
     * @param target The Element
     * @return The XPath as a String
     */
    public abstract String generateXPath(Element target);

    /**
     * Generate the selection query that match the provided element
     * according the Locator condition.
     * This method may thrown an UnsupportedOperationException if not supported
     *
     * @param target The Element
     * @return The selection query in an XPath format@
     * @throws UnsupportedOperationException if not supported
     */
    public abstract String generateCondition(Element target);

}
