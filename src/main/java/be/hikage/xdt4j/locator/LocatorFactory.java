package be.hikage.xdt4j.locator;

import be.hikage.xdt4j.XdtException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LocatorFactory {
    public static Logger LOG = LoggerFactory.getLogger(LocatorFactory.class);

    private static final Pattern LOCATOR_VALIDATOR_PATTERN = Pattern.compile("(\\w*)(\\((.*)\\))?");


    /**
     * Returns an instance corresponding to the value of the attribute "Locator" of the element parameter, or null if the attribute is empty or invalid.
     * In case of the attribute value is not in a valid format, an XdtException will be thrown
     *
     * @param element The Element for which a Locator must be created
     * @return tine {@link Locator} instance, or null
     * @throws XdtException If the attribute is not in a valid format
     */
    public static final Locator createLocator(Element element) {
        String locatorAttributeValue = element.attributeValue("Locator", null);
        if (locatorAttributeValue == null)
            return null;

        Matcher locatorMatcher = extractArgumentAndValidate(locatorAttributeValue);

        String locatorType = locatorMatcher.group(1);
        String locatorParameter = locatorMatcher.group(3);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Locator Type :  {}", locatorType);
            LOG.debug("Locator Parameter : {}", locatorParameter);
        }

        return instantiateImplementation(locatorType, locatorParameter);


    }
    
    private static Locator instantiateImplementation(String locatorType, String locatorParameter){
        if ("Condition".equals(locatorType)) {
            return new ConditionLocator(locatorParameter);
        } else if ("Match".equals(locatorType))
            return new MatchLocator(locatorParameter);
        else if ("XPath".equals(locatorType))
            return new XPathLocator(locatorParameter);

        return null;


    }

    private static Matcher extractArgumentAndValidate(String locatorAttributeValue) {
        Matcher locatorMatcher = LOCATOR_VALIDATOR_PATTERN.matcher(locatorAttributeValue);

        if (!locatorMatcher.matches())
            throw new XdtException("The Transform Attributes value is invalid " + locatorAttributeValue);
        return locatorMatcher;
    }
}
