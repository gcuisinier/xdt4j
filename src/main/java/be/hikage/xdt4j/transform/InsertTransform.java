package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.locator.LocatorUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InsertTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(InsertTransform.class);

    @Override
    public void applyInternal() {

        //List<Element> targetElements = workingDocument.selectNodes(transformElement.getParent().getPath());
        List<Element> targetElements = workingDocument.selectNodes(LocatorUtils.uniqueXPathFromLocator(transformElement.getParent()));

        if (!targetElements.isEmpty()) {
            Element targetElement = targetElements.get(0);

            targetElement.add(getTransformElementCopy());

        }
    }

    public InsertTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
