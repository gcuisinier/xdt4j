package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReplaceTransform extends AbstractFirstChildBasedTransform {

    private static Logger LOG = LoggerFactory.getLogger(ReplaceTransform.class);


    public ReplaceTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }


    @Override
    protected void processElement(Element targetElement) {
        if (LOG.isDebugEnabled())
            LOG.debug("Replace element : {}", targetElement.getPath());

        replaceElement(targetElement, getTransformElementCopy());
    }

    @Override
    protected String getSelectionQuery() {
        return transformElement.getPath();
    }

    private void replaceElement(Element toReplace, Element transformElement) {
        toReplace.clearContent();
        toReplace.setAttributes(transformElement.attributes());
        toReplace.setContent(transformElement.content());
    }
}
