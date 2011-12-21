package be.hikage.xdt4j.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: hikage
 * Date: 21/12/11
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {


    public static Document loadXml(String filename) throws DocumentException {

        InputStream stream = TestUtils.class.getClassLoader().getResourceAsStream(filename);

        SAXReader reader = new SAXReader();
        return reader.read(stream);

    }

    public static Document loadXmlFromString(String xml) throws DocumentException {

        return DocumentHelper.parseText(xml);

    }
}
