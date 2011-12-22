package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.Transform;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class InsertBeforeTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(InsertBeforeTransform.class);

    @Override
    public void applyInternal() {

        List<Element> targetElements = workingDocument.selectNodes(transformElement.getParent().getPath());

        if (!targetElements.isEmpty()) {
            Element targetElement = targetElements.get(0);

            String localPath = getRelativeXPath();

            Element markedElement = (Element) targetElement.selectSingleNode(localPath);
            int indexOfMarkerElement = 0;
            for (Element ptrElement : (List<Element>) targetElement.elements()) {
                if (ptrElement == markedElement) break;
                indexOfMarkerElement++;
            }

            targetElement.elements().add(indexOfMarkerElement, getTransformElementCopy());


        }
    }


    private String getRelativeXPath() {
        String localPath = arguments;
        if (localPath.startsWith(transformElement.getParent().getPath())) {
            localPath = localPath.substring(transformElement.getParent().getPath().length());
            LOG.debug("Simplify Path : {} -> {}", arguments, localPath);
        }
        return localPath;
    }

    public InsertBeforeTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
