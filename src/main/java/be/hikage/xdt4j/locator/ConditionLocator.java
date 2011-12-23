package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtException;
import org.dom4j.Element;

public class ConditionLocator extends Locator {
    public ConditionLocator(String parameter) {
        super(parameter);
        if (parameter == null || parameter.isEmpty())
            throw new XdtException("Parameter is mandatory for ConditionLocator");
    }

    @Override
    public String generateXPath(Element target) {
        return target.getPath() + "[" + getParameter() + "]";
    }


}
