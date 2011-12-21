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
public class InsertAfterTransform extends Transform {

    public static Logger LOG = LoggerFactory.getLogger(InsertAfterTransform.class);

    @Override
    public void applyInternal() {

        List<Element> targetElements = workingDocument.selectNodes(transformElement.getParent().getPath());

        if (!targetElements.isEmpty()) {
            Element targetElement = targetElements.get(0);

            String localPath = arguments;
            if (localPath.startsWith(transformElement.getParent().getPath())) {
                localPath = localPath.substring(transformElement.getParent().getPath().length());
                LOG.debug("Simplify Path : {} -> {}", arguments, localPath);
            }

            Element markedElement = (Element) targetElement.selectSingleNode(localPath);
            int idx = 0;
            for (Element ptrElement : (List<Element>) targetElement.elements()) {
                if (ptrElement == markedElement) break;
                idx++;
            }

            if (idx == targetElement.elements().size())
                targetElement.add(transformElement.createCopy());
            else
                targetElement.elements().add(idx + 1, transformElement.createCopy());


        }
    }

    public InsertAfterTransform(Document workingDocument, Element transformElement, String arguments) {
        super(workingDocument, transformElement, arguments);
    }
}
