package be.hikage.xdt4j.transform;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractXPathSelectionBaseTransform extends Transform {

    private static Logger LOG = LoggerFactory.getLogger(AbstractXPathSelectionBaseTransform.class);


    protected String selectionXPathExpression;

    private final ProcessChildenStrategy processChildenStrategy;


    public AbstractXPathSelectionBaseTransform(Document workingDocument, Element transformElement, String arguments, ProcessChildenStrategy strategy) {
        super(workingDocument, transformElement, arguments);
        this.processChildenStrategy = strategy;

    }


    @Override
    protected final void applyInternal() {
        selectionXPathExpression = getSelectionQuery();
        List<Element> targetElements = workingDocument.selectNodes(selectionXPathExpression);

        if (!targetElements.isEmpty()) {
            processSelection(targetElements);


        } else
            LOG.warn("XPath {} don't match in working document, cannot process", selectionXPathExpression);
    }

    protected void processSelection(List<Element> targetElements) {
        if (processChildenStrategy == ProcessChildenStrategy.FIRST)
            processElement(targetElements.get(0));
        else
            for (Element element : targetElements)
                processElement(element);
    }

    protected abstract void processElement(Element targetElement);

    protected abstract String getSelectionQuery();


    public enum ProcessChildenStrategy {
        FIRST, EACH
    }


}
