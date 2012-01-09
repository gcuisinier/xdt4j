package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.locator.Locator;
import be.hikage.xdt4j.locator.LocatorFactory;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class LocatorUtils {
    public LocatorUtils() {
    }

    public String uniqueXPathFromLocator(Element currentElement) {
        List<String> xpathParts = new ArrayList<String>();

        while (currentElement != null) {
            Locator locator = LocatorFactory.createLocator(currentElement);
            if (locator != null) {
                xpathParts.add(locator.generateCondition(currentElement));
            } else
                xpathParts.add(currentElement.getPath(currentElement.getParent()));

        }


        return "/" + Joiner.on("/").join(Lists.reverse(xpathParts));
    }
}