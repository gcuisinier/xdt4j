package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.XdtConstants;
import be.hikage.xdt4j.locator.Locator;
import be.hikage.xdt4j.locator.LocatorFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public abstract class Transform {

    private static Logger LOG = LoggerFactory.getLogger(Transform.class);


    protected Element transformElement;
    protected Document workingDocument;
    protected String arguments;

    public Transform(Document workingDocument, Element transformElement, String arguments) {
        this.workingDocument = workingDocument;
        this.transformElement = transformElement;
        this.arguments = arguments;
    }


    public void apply() {

        if (LOG.isTraceEnabled())
            LOG.trace("Before applying {}: {}", getClass().getName(), workingDocument.asXML());

        applyInternal();

        if (LOG.isTraceEnabled())
            LOG.trace("After applying {}: {}", getClass().getName(), workingDocument.asXML());


    }

    protected abstract void applyInternal();

    protected Element getTransformElementCopy() {
        Element tempElement = transformElement.createCopy();
        List<Attribute> newAttributes = new ArrayList<Attribute>();

        // Clean XDT Schema Reference
        for (Attribute attribute : (List<Attribute>) tempElement.attributes()) {
            if (XdtConstants.XDT_NAMESPACE.equals(attribute.getNamespaceURI()))
                continue;
            newAttributes.add(attribute);
        }

        tempElement.setAttributes(newAttributes);

        return tempElement;

    }

    protected String getXPath() {
        Locator locator = LocatorFactory.createLocator(transformElement);
        String xpath;
        if (locator != null) {
            xpath = locator.generateXPath(transformElement);
        } else
            xpath = transformElement.getPath();

        LOG.debug("XPath outcome of Locator processing : {}", xpath);
        return xpath;
    }




}
