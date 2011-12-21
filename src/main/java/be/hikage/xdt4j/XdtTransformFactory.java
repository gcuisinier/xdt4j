package be.hikage.xdt4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: hikage
 * Date: 21/12/11
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class XdtTransformFactory {

    public static Logger LOG = LoggerFactory.getLogger(XdtTransformFactory.class);


    private static final Pattern ATTRIBUTE_VALIDATOR_PATTERN = Pattern.compile("(\\w*)(\\((.*)\\))?");

    public static Transform createTransform(Element transformElement, Document workingDocument) {

        String transformAttributeValue = transformElement.attributeValue("Transform");
        Matcher matcher = ATTRIBUTE_VALIDATOR_PATTERN.matcher(transformAttributeValue);

        if (!matcher.matches())
            throw new IllegalArgumentException("The Transform Attributes value is invalid" + transformAttributeValue);


        if (LOG.isDebugEnabled()) {
            LOG.debug("Current Element XPath : {}", transformElement.getPath());
            LOG.debug("Current Element Action :  {}", matcher.group(1));
            LOG.debug("Current Element Parameter :  {}", matcher.group(3));
        }

        try {
            Class<?> transformClass = Class.forName("be.hikage.xdt4j.transform." + matcher.group(1) + "Transform");
            Constructor constructor = transformClass.getConstructor(Document.class, Element.class, String.class);

            Transform instance = (Transform) constructor.newInstance(workingDocument, transformElement, matcher.group(3));

            return instance;


        } catch (Exception e) {
            throw new RuntimeException("TODO : Exception handling");
        }


    }
}
