package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

public class RemoveAllTransform extends RemoveTransform {
    public RemoveAllTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments, ProcessChildenStrategy.EACH);
    }


}
