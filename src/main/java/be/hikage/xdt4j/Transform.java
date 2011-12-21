package be.hikage.xdt4j;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by IntelliJ IDEA.
 * User: hikage
 * Date: 21/12/11
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Transform {


    protected Element transformElement;
    protected Document workingDocument;
    protected String arguments;

    protected Transform(Document workingDocument, Element transformElement, String arguments) {
        this.workingDocument = workingDocument;
        this.transformElement = transformElement;
        this.arguments = arguments;
    }


    public abstract void apply();
}
