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


    public static final Locator createLocator(Element element) {
        String locatorAtrributeValue = element.attributeValue("Locator", null);
        if (locatorAtrributeValue == null)
            return null;

        Matcher locatorMatcher = LOCATOR_VALIDATOR_PATTERN.matcher(locatorAtrributeValue);

        if (!locatorMatcher.matches())
            throw new XdtException("The Transform Attributes value is invalid " + locatorAtrributeValue);


        String locatorType = locatorMatcher.group(1);
        String locatorParameter = locatorMatcher.group(3);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Locator Type :  {}", locatorType);
            LOG.debug("Locator Parameter : {}", locatorParameter);
        }

        if ("Condition".equals(locatorType)) {
            return new ConditionLocator(locatorParameter);
        } else if ("Match".equals(locatorType))
            return new MatchLocator(locatorParameter);

        return null;


    }
}
