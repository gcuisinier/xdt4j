package be.hikage.xdt4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hikage
 * Date: 21/12/11
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
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

        if (LOG.isDebugEnabled())
            LOG.debug("Before applying {}: {}", getClass().getName(), workingDocument.asXML());

        applyInternal();

        if (LOG.isDebugEnabled())
            LOG.debug("After applying {}: {}", getClass().getName(), workingDocument.asXML());


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