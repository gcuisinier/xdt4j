package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.locator.LocatorUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RemoveTransform extends AbstractXPathSelectionBaseTransform {

    private static Logger LOG = LoggerFactory.getLogger(RemoveTransform.class);


    public RemoveTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments, ProcessChildenStrategy.FIRST);
    }

    protected RemoveTransform(Document workingDocument, Element transformElement, String arguments, ProcessChildenStrategy strategy) {
        super(workingDocument, transformElement, arguments, strategy);
    }

    @Override
    protected void processElement(Element targetElement) {
        if (LOG.isDebugEnabled())
            LOG.debug("Removing element : {}", targetElement.getPath());


        targetElement.getParent().remove(targetElement);
    }

    @Override
    protected String getSelectionQuery() {
        return LocatorUtils.generateSpecificXPath(transformElement);
        //return transformElement.getPath();
    }


}
