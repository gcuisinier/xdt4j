package be.hikage.xdt4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public abstract class Transform {

    public static Logger LOG = LoggerFactory.getLogger(Transform.class);


    protected Element transformElement;
    protected Document workingDocument;
    protected String arguments;

    protected Transform(Document workingDocument, Element transformElement, String arguments) {
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

        for (Attribute attribute : (List<Attribute>) tempElement.attributes()) {
            if (XdtConstants.XDT_NAMESPACE.equals(attribute.getNamespaceURI()))
                continue;
            newAttributes.add(attribute);
        }

        tempElement.setAttributes(newAttributes);

        return tempElement;

    }

}
