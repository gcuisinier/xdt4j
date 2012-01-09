package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAllChildBasedTransform extends AbstractXPathSelectionBaseTransform {

    private static Logger LOG = LoggerFactory.getLogger(AbstractAllChildBasedTransform.class);


    public AbstractAllChildBasedTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments, ProcessChildenStrategy.EACH);
    }


}
