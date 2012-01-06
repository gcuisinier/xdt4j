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

    public abstract String generateXPath(Element target);

    public abstract String generateCondition(Element target);

}
