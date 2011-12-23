package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ReplaceTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(ReplaceTransform.class);


    public ReplaceTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }

    @Override
    public void applyInternal() {


        List<Element> targetElements = workingDocument.selectNodes(transformElement.getPath());

        if (targetElements.size() >= 1) {
            Element toReplace = targetElements.get(0);
            if (LOG.isDebugEnabled())
                LOG.debug("Replace element : {}", toReplace.getPath());

            replaceElement(toReplace, getTransformElementCopy());

        }


    }

    private void replaceElement(Element toReplace, Element transformElement) {
        toReplace.clearContent();
        toReplace.setAttributes(transformElement.attributes());
        toReplace.setContent(transformElement.content());
    }
}
