package be.hikage.xdt4j.transform;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;


public class RemoveAttributesTransform extends AbstractAllChildBasedTransform {


    @Override
    protected void processElement(Element targetElement) {
        for (Attribute attribute : (List<Attribute>) getTransformElementCopy().attributes()) {
            if (mustBeRemove(attribute.getQName().getName())) {
                Attribute attributeToRemove = targetElement.attribute(attribute.getQName());
                targetElement.remove(attributeToRemove);
            }
        }
    }

    @Override
    protected String getSelectionQuery() {
        return getXPath();
    }

    private boolean mustBeRemove(String name) {
        if (arguments == null || arguments.isEmpty())
            return true;
        return name.equals(arguments);
    }

    public RemoveAttributesTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
