package be.hikage.xdt4j.util;

import org.dom4j.Element;

import java.util.List;

public abstract class XmlUtils {


    public static int findIndexOfElementInChildren(Element containerElement, Element markedElement) {
        int indexOfMarkerElement = 0;
        for (Element ptrElement : (List<Element>) containerElement.elements()) {
            if (ptrElement == markedElement) break;
            indexOfMarkerElement++;
        }
        return indexOfMarkerElement;
    }
}