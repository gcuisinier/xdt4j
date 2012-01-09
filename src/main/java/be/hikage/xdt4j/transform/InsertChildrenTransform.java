package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InsertChildrenTransform extends Transform {

    private static Logger LOG = LoggerFactory.getLogger(InsertChildrenTransform.class);


    public InsertChildrenTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }

    @Override
    protected void applyInternal() {
        List<Element> elementsToInsert = transformElement.elements();

        Element targetElement = (Element) workingDocument.selectSingleNode(getXPath());

        if (targetElement != null) {
            for (Element element : elementsToInsert) {
                targetElement.add(element.createCopy());
            }
        } else
            LOG.warn("XPath {} don't match in working document, cannot insert element", getXPath());


    }
}
