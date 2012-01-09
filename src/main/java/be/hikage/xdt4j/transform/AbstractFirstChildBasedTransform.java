package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractFirstChildBasedTransform extends AbstractXPathSelectionBaseTransform {

    private static Logger LOG = LoggerFactory.getLogger(AbstractFirstChildBasedTransform.class);


    public AbstractFirstChildBasedTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments, ProcessChildenStrategy.FIRST);
    }





}
