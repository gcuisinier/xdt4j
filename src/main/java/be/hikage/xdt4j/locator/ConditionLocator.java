package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.locator.Locator;
import org.dom4j.Element;

public class ConditionLocator extends Locator {
    public ConditionLocator(String parameter) {
        super(parameter);
    }

    @Override
    public String generateXPath(Element target) {
        return target.getPath() + "[" + getParameter() + "]";
    }


}
