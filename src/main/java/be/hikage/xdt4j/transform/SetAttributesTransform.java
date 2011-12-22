package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.Transform;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;


public class SetAttributesTransform extends Transform {
    @Override
    public void applyInternal() {
        List<Element> targetElements = workingDocument.selectNodes(transformElement.getPath());
        if (!targetElements.isEmpty()) {
            Element targetElement = targetElements.get(0);
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
