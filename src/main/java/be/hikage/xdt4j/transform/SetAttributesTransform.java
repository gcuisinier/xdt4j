package be.hikage.xdt4j.transform;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SetAttributesTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(SetAttributesTransform.class);


    @Override
    public void applyInternal() {
        List<Element> targetElements = workingDocument.selectNodes(getXPath());
        if (!targetElements.isEmpty()) {
            for (Element targetElement : targetElements)
                for (Attribute attribute : (List<Attribute>) getTransformElementCopy().attributes()) {
                    if (mustBeSet(attribute.getQName().getName()))
                        targetElement.addAttribute(attribute.getQName(), attribute.getValue());
                }
        }

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
