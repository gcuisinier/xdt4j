package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtException;
import org.dom4j.Element;

/**
 * Locator which is based on an relative XPath query on to create the selection
 */
public class ConditionLocator extends Locator {
    public ConditionLocator(String parameter) {
        super(parameter);
        if (parameter == null || parameter.isEmpty())
            throw new XdtException("Parameter is mandatory for ConditionLocator");
    }

    @Override
    public String generateXPath(Element target) {
        return target.getPath() + generateCondition(target);
    }

    @Override
    public String generateCondition(Element target) {
        return "[" + getParameter() + "]";
    }


}
