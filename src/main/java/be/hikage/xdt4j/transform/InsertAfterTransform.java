package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.locator.LocatorUtils;
import be.hikage.xdt4j.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InsertAfterTransform extends AbstractFirstChildBasedTransform {

    private static Logger LOG = LoggerFactory.getLogger(InsertAfterTransform.class);


    @Override
    protected void processElement(Element targetElement) {
        String localPath = getRelativeXPath();

        Element markedElement = (Element) targetElement.selectSingleNode(localPath);
        int indexOfMarkerElement = XmlUtils.findIndexOfElementInChildren(targetElement, markedElement);

        if (indexOfMarkerElement == targetElement.elements().size() - 1)
            targetElement.add(getTransformElementCopy());
        else
            targetElement.elements().add(indexOfMarkerElement + 1, getTransformElementCopy());
    }

    @Override
    protected String getSelectionQuery() {
        return LocatorUtils.generateSpecificXPath(transformElement.getParent());
    }

    private String getRelativeXPath() {
        String localPath = arguments;
        if (localPath.startsWith(transformElement.getParent().getPath())) {
            localPath = localPath.substring(transformElement.getParent().getPath().length() + 1);
            LOG.debug("Simplify Path : {} -> {}", arguments, localPath);
        }
        return localPath;
    }

    public InsertAfterTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
