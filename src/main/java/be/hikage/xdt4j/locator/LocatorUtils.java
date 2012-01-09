package be.hikage.xdt4j.locator;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class LocatorUtils {

    private static Logger LOG = LoggerFactory.getLogger(LocatorUtils.class);


    /**
     * Create an specific XPath to the provided Element by using the Locators information
     *
     * @param currentElement
     * @return an specific XPath that point to the element
     * @throws be.hikage.xdt4j.XdtException can be thrown if the hierarchy of the element present an Locator
     *                                      that does not allow call to {@see Locator.generateCondition}
     */
    public static String generateSpecificXPath(Element currentElement) {
        List<String> xpathParts = new ArrayList<String>();

        while (currentElement != null) {
            Locator locator = LocatorFactory.createLocator(currentElement);
            if (locator != null) {
                String currentNode = currentElement.getPath(currentElement.getParent());
                xpathParts.add(currentNode + locator.generateCondition(currentElement));
            } else {
                xpathParts.add(currentElement.getPath(currentElement.getParent()));
            }
            currentElement = currentElement.getParent();
        }

        String resultXPath = Joiner.on("/").join(Lists.reverse(xpathParts));

        LOG.debug("XPath outcome of Locators processing : {}", resultXPath);
        return resultXPath;
    }
}