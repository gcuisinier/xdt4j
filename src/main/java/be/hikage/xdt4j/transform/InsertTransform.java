package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.locator.LocatorUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertTransform extends AbstractFirstChildBasedTransform {

    private static Logger LOG = LoggerFactory.getLogger(InsertTransform.class);


    @Override
    protected void processElement(Element targetElement) {
        targetElement.add(getTransformElementCopy());
    }

    @Override
    protected String getSelectionQuery() {
        return LocatorUtils.generateSpecificXPath(transformElement.getParent());
    }

    public InsertTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
