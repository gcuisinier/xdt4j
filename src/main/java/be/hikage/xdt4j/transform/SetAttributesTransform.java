package be.hikage.xdt4j.transform;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SetAttributesTransform extends AbstractAllChildBasedTransform {

    private static Logger LOG = LoggerFactory.getLogger(SetAttributesTransform.class);


    @Override
    protected void processElement(Element targetElement) {
        for (Attribute attribute : (List<Attribute>) getTransformElementCopy().attributes()) {
            if (mustBeSet(attribute.getQName().getName()))
                targetElement.addAttribute(attribute.getQName(), attribute.getValue());
        }
    }

    @Override
    protected String getSelectionQuery() {
        return getXPath();
    }


    private boolean mustBeSet(String name) {
        if (arguments == null || arguments.isEmpty())
            return true;
        return name.equals(arguments);
    }

    public SetAttributesTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
