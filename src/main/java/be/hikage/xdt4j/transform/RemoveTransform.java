package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.Transform;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class RemoveTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(RemoveTransform.class);


    public RemoveTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }

    @Override
    public void applyInternal() {

        List<Element> targetElements = workingDocument.selectNodes(transformElement.getPath());

        if (targetElements.size() >= 1) {
            Element toRemove = targetElements.get(0);
            if (LOG.isDebugEnabled())
                LOG.debug("Removing element : {}", toRemove.getPath());

            applyRemove(toRemove.getParent(), targetElements);

        }


    }

    protected void applyRemove(Element containerElement, List<Element> targetElements) {
        containerElement.remove(targetElements.get(0));
    }
}
