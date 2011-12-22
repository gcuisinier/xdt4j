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
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
public class InsertTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(InsertTransform.class);

    @Override
    public void applyInternal() {

        List<Element> targetElements = workingDocument.selectNodes(transformElement.getParent().getPath());

        if (!targetElements.isEmpty()) {
            Element targetElement = targetElements.get(0);

            targetElement.add(getTransformElementCopy());

        }
    }

    public InsertTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
