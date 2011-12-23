package be.hikage.xdt4j;

import be.hikage.xdt4j.transform.Transform;
import be.hikage.xdt4j.transform.XdtTransformFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XdtTransformer {

    public static Logger log = LoggerFactory.getLogger(XdtTransformer.class);


    public Document transform(Document inputDocument, Document transformDocument) {

        XPath xPath = DocumentHelper.createXPath("//*[@xdt:Transform]");
        xPath.setNamespaceURIs(Collections.singletonMap("xdt", XdtConstants.XDT_NAMESPACE));


        Document workingCopy = (Document) inputDocument.clone();

        List<Element> xdtNode = xPath.selectNodes(transformDocument);

        List<Transform> transformToApply = new ArrayList<Transform>();

        if (log.isInfoEnabled())
            log.info("Found {} elements to process", xdtNode.size());
        for (Element xdtElement : xdtNode) {
            transformToApply.add(XdtTransformFactory.createTransform(xdtElement, workingCopy));
        }


        for (Transform tr : transformToApply)
            tr.apply();

        return workingCopy;
    }
}
