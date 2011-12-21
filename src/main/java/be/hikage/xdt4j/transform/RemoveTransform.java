package be.hikage.xdt4j.transform;

import be.hikage.xdt4j.Transform;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hikage
 * Date: 21/12/11
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class RemoveTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(RemoveTransform.class);


    public RemoveTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }

    @Override
    public void apply() {
        if (LOG.isDebugEnabled())
            LOG.debug("Before applying : {}", workingDocument.asXML());

        List<Element> targetElements = workingDocument.selectNodes(transformElement.getPath());

        if (targetElements.size() >= 1) {
            Element toRemove = targetElements.get(0);
            if (LOG.isDebugEnabled())
                LOG.debug("Removing element : {}", toRemove.getPath());

            toRemove.getParent().remove(toRemove);

        }
        if (LOG.isDebugEnabled())
            LOG.debug("After removing {}", workingDocument.asXML());

    }
}
