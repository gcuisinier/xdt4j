package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

public class InsertChildrenTransform extends Transform {
    public InsertChildrenTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }

    @Override
    protected void applyInternal() {
        List<Element> elementsToInsert = transformElement.elements();

        Element targetElement = (Element) workingDocument.selectSingleNode(getXPath());

        for(Element element : elementsToInsert){
            targetElement.add(element.createCopy());
        }


    }
}
